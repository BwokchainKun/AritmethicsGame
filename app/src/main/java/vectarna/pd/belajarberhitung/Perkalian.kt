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

class Perkalian : BaseActivity() {
    var nilai1 =0
    var nilai2 =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perkalian)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val pilihan = intent.getStringExtra("value_pilihan")
        val titlePenjumlahan: TextView = findViewById(R.id.titlePenjumlahan)
        when (pilihan) {
            "1" -> titlePenjumlahan.text = "Perkalian 1"
            "2" -> titlePenjumlahan.text = "Perkalian 2"
            "3" -> titlePenjumlahan.text = "Perkalian 3"
            "4" -> titlePenjumlahan.text = "Perkalian 4"
            "5" -> titlePenjumlahan.text = "Perkalian 5"
            "6" -> titlePenjumlahan.text = "Perkalian 6"
            "7" -> titlePenjumlahan.text = "Perkalian 7"
            "8" -> titlePenjumlahan.text = "Perkalian 8"
            "9" -> titlePenjumlahan.text = "Perkalian 9"
            "10" -> titlePenjumlahan.text = "Perkalian 10"
            "11" -> titlePenjumlahan.text = "Perkalian Campuran"
            else -> titlePenjumlahan.text = "Tidak diketahui"

        }
        val addition1: TextView = findViewById(R.id.add1)
        val addition2: TextView = findViewById(R.id.addition2)
        val answer: EditText = findViewById(R.id.etAnswer)
        val submit: Button = findViewById(R.id.checkAnswerbtn)
        val hasil: TextView = findViewById(R.id.score)

        var endvalue = when (pilihan) {
            "1" -> 1
            "2" -> 2
            "3" -> 3
            "4" -> 4
            "5" -> 5
            "6" -> 6
            "7" -> 7
            "8" -> 8
            "9" -> 9
            "10" -> 10
            "11" -> 11
            else -> 1
        }

        // Generate initial numbers
        if (endvalue == 1){
            nilai1 = 1
            nilai2 = (0..12).random()
        } else if (endvalue == 2) {
            nilai1 = 2
            nilai2 = (0..12).random()
        }else if (endvalue == 3) {
            nilai1 = 3
            nilai2 = (0..12).random()
        }else if (endvalue == 4) {
            nilai1 = 4
            nilai2 = (0..12).random()
        }else if (endvalue == 5) {
            nilai1 = 5
            nilai2 = (0..12).random()
        }else if (endvalue == 6) {
            nilai1 = 6
            nilai2 = (0..12).random()
        }else if (endvalue == 7) {
            nilai1 = 7
            nilai2 = (0..12).random()
        }else if (endvalue == 8) {
            nilai1 = 8
            nilai2 = (0..12).random()
        }else if (endvalue == 9) {
            nilai1 = 9
            nilai2 = (0..12).random()
        }else if (endvalue == 10) {
            nilai1 = 10
            nilai2 = (0..12).random()

        } else if (endvalue == 11) {
            nilai1 = (1..10).random()
            nilai2 = (0..12).random()

        } else {
            nilai1 = (0..10).random()
            nilai2 = (0..endvalue).random()
        }
        addition1.text = nilai1.toString()
        addition2.text = nilai2.toString()
        hasil.text = "Skor:" + GlobalObject.score.toString()

        submit.setOnClickListener {
            val jawaban = answer.text.toString().toIntOrNull()
            if (jawaban==null) {
                GlobalObject.showToast(this@Perkalian, "Ketik Jawaban yg Benar")
                wrongSound(this)
            } else if (jawaban!==null){
                if (CekJawaban(nilai1, nilai2, jawaban)) {
                    GlobalObject.score += 1
                    correctSound(this)
                    GlobalObject.showToast(this@Perkalian, "Jawaban Benar")

                    // Regenerate new numbers only if correct
                    if (endvalue == 1){
                        nilai1 = 1
                        nilai2 = (0..12).random()
                    } else if (endvalue == 2) {
                        nilai1 = 2
                        nilai2 = (0..12).random()
                    }else if (endvalue == 3) {
                        nilai1 = 3
                        nilai2 = (0..12).random()
                    }else if (endvalue == 4) {
                        nilai1 = 4
                        nilai2 = (0..12).random()
                    }else if (endvalue == 5) {
                        nilai1 = 5
                        nilai2 = (0..12).random()
                    }else if (endvalue == 6) {
                        nilai1 = 6
                        nilai2 = (0..12).random()
                    }else if (endvalue == 7) {
                        nilai1 = 7
                        nilai2 = (0..12).random()
                    }else if (endvalue == 8) {
                        nilai1 = 8
                        nilai2 = (0..12).random()
                    }else if (endvalue == 9) {
                        nilai1 = 9
                        nilai2 = (0..12).random()
                    }else if (endvalue == 10) {
                        nilai1 = 10
                        nilai2 = (0..12).random()

                    } else if (endvalue == 11) {
                        nilai1 = (1..10).random()
                        nilai2 = (0..12).random()

                    } else {
                        nilai1 = (0..10).random()
                        nilai2 = (0..endvalue).random()
                    }
                    addition1.text = nilai1.toString()
                    addition2.text = nilai2.toString()

                    answer.text.clear()
                }
                else {
                    GlobalObject.score -= 1
                    wrongSound(this)
                    GlobalObject.showToast(this@Perkalian, "Jawaban Salah")
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
        val soal = nilai1 * nilai2
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
