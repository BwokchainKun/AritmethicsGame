package vectarna.pd.belajarberhitung

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Process
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import vectarna.pd.belajarberhitung.GlobalObject.Companion.exitCode
import vectarna.pd.belajarberhitung.GlobalObject.Companion.maxPoint
import vectarna.pd.belajarberhitung.GlobalObject.Companion.score
import vectarna.pd.belajarberhitung.GlobalObject.Companion.showToast


class MainActivity : AppCompatActivity() {
    private var isFirstResume = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        //Call Dialog Function To ask About Pin and Jumlah Soal
        showInputDialog()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.addition1)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }


        // Tombol-tombol di Home
        val btnAddition: Button = findViewById(R.id.addition)
        val btnSubtraction: Button = findViewById(R.id.substraction)
        val btnMultiplication: Button = findViewById(R.id.multiplication)
        val btnPerkalianCustom: Button = findViewById(R.id.perkaliancustom)
        val exitApp: Button = findViewById(R.id.exitAppBtn)
        //Tombol Menu END

        //Tombol ke Menu Penjumlahan Start
        btnAddition.setOnClickListener {
            val options =
                arrayOf("Penjumlahan 1 Angka", "Penjumlahan 2 Angka", "Penjumlahan Campuran")
            var pilihan = 9
            val builder = AlertDialog.Builder(this)
            builder
                .setTitle("Pilih Jenis Penjumlahan")
                .setSingleChoiceItems(options, 0) { _, which ->
                    pilihan = when (which) {
                        0 -> 9
                        1 -> 99
                        2 -> 100
                        else -> 9
                    }
                }
                .setPositiveButton("Lanjutkan") { _, _ ->
                    val intent = Intent(this, Penjumlahan::class.java)
                    intent.putExtra("value_pilihan", pilihan.toString())
                    startActivity(intent)
                }
                .setNegativeButton("Kembali") { dialog, _ ->
                    dialog.cancel()
                }

            builder.create().show()
        }
        //Tombol Penjumlahan END

        //Tombol Pengurangan
        btnSubtraction.setOnClickListener {
            val options =
                arrayOf("Pengurangan 1 Angka", "Pengurangan 2 Angka", "Pengurangan Campuran")
            var pilihan = 9
            val builder = AlertDialog.Builder(this)
            builder
                .setTitle("Pilih Jenis Pengurangan")
                .setSingleChoiceItems(options, 0) { _, which ->
                    pilihan = when (which) {
                        0 -> 9
                        1 -> 99
                        2 -> 100
                        else -> 9
                    }
                }
                .setPositiveButton("Lanjutkan") { _, _ ->
                    val intent = Intent(this, Pengurangan::class.java)
                    intent.putExtra("value_pilihan", pilihan.toString())
                    startActivity(intent)
                }
                .setNegativeButton("Kembali") { dialog, _ ->
                    dialog.cancel()
                }

            builder.create().show()
        }
        //Tombol Pengurangan END

        //Tombol Perkalian Start
        btnMultiplication.setOnClickListener {
            val options = arrayOf(
                "Perkalian 1",
                "Perkalian 2",
                "Perkalian 3",
                "Perkalian 4",
                "Perkalian 5",
                "Perkalian 6",
                "Perkalian 7",
                "Perkalian 8",
                "Perkalian 9",
                "Perkalian 10",
                "Perkalian Campuran"
            )
            var pilihan = 1
            val builder = AlertDialog.Builder(this)
            builder
                .setTitle("Pilih Jenis Perkalian")
                .setSingleChoiceItems(options, 0) { _, which ->
                    pilihan = when (which) {
                        0 -> 1
                        1 -> 2
                        2 -> 3
                        3 -> 4
                        4 -> 5
                        5 -> 6
                        6 -> 7
                        7 -> 8
                        8 -> 9
                        9 -> 10
                        10 -> 11
                        else -> 1
                    }
                }
                .setPositiveButton("Lanjutkan") { _, _ ->
                    val intent = Intent(this, Perkalian::class.java)
                    intent.putExtra("value_pilihan", pilihan.toString())
                    Log.d(
                        "INTENT_DEBUG",
                        "Sending intent to Penjumlahan with value_pilihan: ${pilihan.toString()}"
                    )
                    startActivity(intent)
                }
                .setNegativeButton("Kembali") { dialog, _ ->
                    dialog.cancel()
                }

            builder.create().show()
        }
        //Tombol Perkalian END

        //Perkalian Custom Start
        btnPerkalianCustom.setOnClickListener {
            showMultiplicationRangeDialog()
        }
        //Perkalian Custom END

        //EXIT BUTTON START
        exitApp.setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_exit, null)
            val pinInput = dialogView.findViewById<EditText>(R.id.pin_input)
            val statusText = dialogView.findViewById<TextView>(R.id.status_text)
            val biometricBtn = dialogView.findViewById<Button>(R.id.biometric_button)

            if (score >= maxPoint) {
                statusText.text = "PIN: $exitCode"
            } else {
                statusText.text = ""
            }

            val dialog = AlertDialog.Builder(this)
                .setTitle("Masukkan PIN")
                .setView(dialogView)
                .setPositiveButton("Submit") { _, _ ->
                    val enteredPin = pinInput.text.toString()
                    if (enteredPin.toIntOrNull() == exitCode) {
                        //QUIT TO HOME SCREEN
                        val intent = Intent(Intent.ACTION_MAIN)
                        intent.addCategory(Intent.CATEGORY_HOME)
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        onQuitPressed()
                        //QUIT TO HOME SCREEN END
                    } else {
                        Toast.makeText(this, "PIN Salah", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Batal", null)
                .create()

            biometricBtn.setOnClickListener {
                showBiometricPrompt {
                    dialog.dismiss()
                    //QUIT TO HOME SCREEN
                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.addCategory(Intent.CATEGORY_HOME)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    onQuitPressed()
                    //QUIT TO HOME SCREEN END

                }
            }

            dialog.show()
        }
        //EXIT BUTTON END
    }

    //Function for DIalog Validation
    private fun showInputDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_input, null)
        val inputJumlahSoal: TextInputEditText =
            dialogView.findViewById<TextInputEditText>(R.id.inputJumlahSoal)
        val inputPin: TextInputEditText = dialogView.findViewById<TextInputEditText>(R.id.inputPin)
        val btnSubmit: Button = dialogView.findViewById<Button>(R.id.btnSubmit)
        val btnCancel: Button = dialogView.findViewById<Button>(R.id.btnCancel)

        val dialog = MaterialAlertDialogBuilder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        btnSubmit.setOnClickListener {
            val jumlahSoal = inputJumlahSoal.text.toString().trim()
            val pin = inputPin.text.toString().trim()

            // Reset errors
            inputJumlahSoal.error = null
            inputPin.error = null

            // Validation
            when {
                jumlahSoal.isEmpty() -> {
                    inputJumlahSoal.error = "Jumlah soal harus diisi"
                    GlobalObject.showToast(this@MainActivity, "Jumlah soal tidak boleh kosong")
                }

                jumlahSoal.toIntOrNull() == null -> {
                    inputJumlahSoal.error = "Harus berupa angka"
                    showToast(this@MainActivity, "Jumlah soal harus berupa angka")
                }

                jumlahSoal.toInt() < 0 -> {
                    inputJumlahSoal.error = "Tidak boleh negatif"
                    showToast(this@MainActivity, "Jumlah soal tidak boleh negatif")
                }

                pin.isEmpty() -> {
                    inputPin.error = "PIN harus diisi"
                    showToast(this@MainActivity, "PIN tidak boleh kosong")
                }

                pin.length != 4 -> {
                    inputPin.error = "PIN harus 4 digit"
                    showToast(this@MainActivity, "PIN harus terdiri dari 4 angka")
                }

                pin.toIntOrNull() == null -> {
                    inputPin.error = "Harus berupa angka"
                    showToast(this@MainActivity, "PIN harus berupa angka")
                }

                else -> {
                    val jumlah = jumlahSoal.toInt()
                    val pinNumber = pin.toInt()
                    GlobalObject.maxPoint = jumlah
                    GlobalObject.exitCode = pinNumber
                    val maxPoint: TextView = findViewById(R.id.maxPoint)
                    maxPoint.text = GlobalObject.maxPoint.toString()

                    showToast(this@MainActivity, "Pin dan Jumlah Soal berhasil diinput")

                    // Jalankan dialog kunci setelah input dialog benar-benar ditutup
                    dialog.setOnDismissListener {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            AlertDialog.Builder(this)
                                .setTitle("Kunci Aplikasi")
                                .setMessage("Apakah Anda ingin mengunci aplikasi untuk anak-anak?")
                                .setPositiveButton("Ya") { _, _ -> startLockTask() }
                                .setNegativeButton("Tidak", null)
                                .show()
                        }
                    }

                    dialog.dismiss()
                }
            }
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
            finish()
        }

        dialog.show()
    }

    fun showMultiplicationRangeDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.jarak_perkalian, null)
        val inputMin = dialogView.findViewById<EditText>(R.id.inputMin)
        val inputMax = dialogView.findViewById<EditText>(R.id.inputMax)

        AlertDialog.Builder(this)
            .setTitle("Masukkan Jarak Perkalian")
            .setView(dialogView)
            .setPositiveButton("OK") { _, _ ->
                val min = inputMin.text.toString().toInt()
                val max = inputMax.text.toString().toInt()
                if (max == null || min == null) {
                    GlobalObject.showToast(this@MainActivity, "Masukkan Jarak Perkalian yang benar")
                } else {
                    GlobalObject.perkalianMin = min
                    GlobalObject.perkalianMax = max
                    startActivity(Intent(this@MainActivity, perkalianCustom::class.java))
                }


            }
            .setNegativeButton("Batal", null)
            .show()
    }


    override fun onResume() {
        super.onResume()
        val scoreview: TextView = findViewById(R.id.scoreview)
        val maxPoint: TextView = findViewById(R.id.maxPoint)
        scoreview.text = GlobalObject.score.toString() + " Soal"
        maxPoint.text = GlobalObject.maxPoint.toString() + " Poin"

        if (!isFirstResume && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !isAppPinned()) {
            askToReenterPinMode()
        }

        isFirstResume = false
    }


    //Biometrics Funstion
    private fun showBiometricPrompt(onSuccess: () -> Unit) {
        val biometricManager = BiometricManager.from(this) // Unresolved reference: from
        if (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) ==
            BiometricManager.BIOMETRIC_SUCCESS
        ) {

            val promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autentikasi Biometrik")
                .setNegativeButtonText("Batal")
                .build()

            val biometricPrompt = BiometricPrompt(this,
                ContextCompat.getMainExecutor(this),
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        onSuccess()
                    }

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        Toast.makeText(applicationContext, "Error: $errString", Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        Toast.makeText(applicationContext, "Autentikasi gagal", Toast.LENGTH_SHORT)
                            .show()
                    }
                })

            biometricPrompt.authenticate(promptInfo)
        } else {
            Toast.makeText(this, "Biometrik tidak tersedia", Toast.LENGTH_SHORT).show()
        }
    }
    //Biometric FUNCTION END

    override fun onBackPressed() {
        super.onBackPressed()
        // Tidak melakukan apapun agar pengguna tidak bisa keluar dengan tombol back
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
            .setMessage("Ingin mengunci aplikasi kembali untuk anak-anak?")
            .setPositiveButton("Ya") { _, _ ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startLockTask()
                }
            }
            .setNegativeButton("Tidak", null)
            .show()
    }
    fun onQuitPressed() {
        val pid = Process.myPid()
        Process.killProcess(pid)
    }
}
