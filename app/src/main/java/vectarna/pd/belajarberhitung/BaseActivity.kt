package vectarna.pd.belajarberhitung

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    private var isFirstResume = true

    override fun onResume() {
        super.onResume()

        if (!isFirstResume && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !isAppPinned()) {
            askToReenterPinMode()
        }

        isFirstResume = false
    }

    private fun isAppPinned(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val am = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            return am.lockTaskModeState != ActivityManager.LOCK_TASK_MODE_NONE
        }
        return false
    }

    private fun askToReenterPinMode() {
        AlertDialog.Builder(this)
            .setTitle("Kunci Aplikasi")
            .setMessage("Aplikasi tidak lagi terkunci. Ingin menguncinya kembali?")
            .setPositiveButton("Ya") { _, _ ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startLockTask()
                }
            }
            .setNegativeButton("Tidak", null)
            .show()
    }
}