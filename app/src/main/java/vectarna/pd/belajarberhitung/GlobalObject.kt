package vectarna.pd.belajarberhitung

import android.content.Context
import android.media.MediaPlayer
import android.widget.Toast

class GlobalObject {
    companion object {
        fun showToast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
        var score = 0
        var maxPoint = 0
        var exitCode = 0
        var perkalianMin =0
        var perkalianMax=0
        var isLockTaskRequested: Boolean = false


        fun correctSound(context: Context) {
            MediaPlayer.create(context, R.raw.correct).start()
        }
       fun wrongSound(context: Context){
           MediaPlayer.create(context, R.raw.wrong).start()
       }



    }
}