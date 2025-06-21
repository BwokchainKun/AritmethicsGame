package vectarna.pd.belajarberhitung

import android.animation.ObjectAnimator
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import vectarna.pd.belajarberhitung.GlobalObject.Companion.correctSound
import vectarna.pd.belajarberhitung.GlobalObject.Companion.wrongSound


class Pengurangan : BaseActivity() {
    var nilai1 =0
    var nilai2 =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pengurangan)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val pilihan = intent.getStringExtra("value_pilihan")
        val titlePenjumlahan: TextView = findViewById(R.id.titlePenjumlahan)
        when (pilihan) {
            "9" -> titlePenjumlahan.text = "Pengurangan 1 Angka"
            "99" -> titlePenjumlahan.text = "Pengurangan 2 Angka"
            "100" -> titlePenjumlahan.text = "Pengurangan Campuran"
            else -> titlePenjumlahan.text = "Tidak diketahui"
        }
        val addition1: TextView = findViewById(R.id.add1)
        val addition2: TextView = findViewById(R.id.addition2)
        val answer: EditText = findViewById(R.id.etAnswer)
        val submit: Button = findViewById(R.id.checkAnswerbtn)
        val hasil: TextView = findViewById(R.id.score)

        var endvalue = when (pilihan) {
            "9" -> 9
            "99" -> 99
            "100" -> 100
            else -> 9
        }

        // Generate initial numbers
        if (endvalue == 9){
            nilai1 = (0..endvalue).random()
            nilai2 = (0..endvalue).random()
        } else if (endvalue == 99) {
            nilai1 = (10..endvalue).random()
            nilai2 = (10..endvalue).random()
        } else {
            nilai1 = (0..10).random()
            nilai2 = (0..endvalue-1).random()
        }
        addition1.text = nilai1.toString()
        addition2.text = nilai2.toString()
        hasil.text = "Soal yg Dikerjakan:" + GlobalObject.score.toString() + "Soal"

        submit.setOnClickListener {
            val jawaban = answer.text.toString().toIntOrNull()
            if (jawaban==null) {
                GlobalObject.showToast(this@Pengurangan, "Ketik Jawaban yg Benar")
                wrongSound(this@Pengurangan)
            } else if (jawaban!==null){
                if (CekJawaban(nilai1, nilai2, jawaban)) {
                    GlobalObject.score += 1
                    correctSound(this@Pengurangan)
                    GlobalObject.showToast(this@Pengurangan, "Jawaban Benar")

                    // Regenerate new numbers only if correct
                    if (endvalue == 9){
                        nilai1 = (0..endvalue).random()
                        nilai2 = (0..endvalue).random()
                    } else if (endvalue == 99) {
                        nilai1 = (10..endvalue).random()
                        nilai2 = (10..endvalue).random()
                    } else {
                        nilai1 = (0..10).random()
                        nilai2 = (0..endvalue-1).random()
                    }
                    addition1.text = nilai1.toString()
                    addition2.text = nilai2.toString()

                    answer.text.clear()
                }
                else {
                    GlobalObject.score -= 1
                    wrongSound(this@Pengurangan)
                    GlobalObject.showToast(this@Pengurangan, "Jawaban Salah")
                    answer.text.clear()
                }
            }

            hasil.text = "Soal yg Dikerjakan:" + GlobalObject.score.toString() + " " + "Soal"
            val progressBar: ProgressBar = findViewById(R.id.progressBar)
            //Change Later
            progressBar.max = GlobalObject.maxPoint
            //Change Value Later ||||||

            ObjectAnimator.ofInt(progressBar, "progress", GlobalObject.score)
                .start()
        }

    }
    fun CekJawaban(nilai1: Int, nilai2: Int, answer: Int?): Boolean {
        val soal = nilai1 - nilai2
        if (answer==soal){
            return true
        } else {
            return false
        }
    }
    override fun onResume() {
        super.onResume()
        val progressBar: ProgressBar = findViewById(R.id.progressBar)
        //Change Later
        progressBar.max = GlobalObject.maxPoint
        ObjectAnimator.ofInt(progressBar, "progress", GlobalObject.score)
            .start()

    }
}