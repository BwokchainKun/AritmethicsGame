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

class perkalianCustom : BaseActivity() {
    var nilai1 = 0
    var nilai2 =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perkalian_custom)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val titlePenjumlahan: TextView = findViewById(R.id.titlePenjumlahan)
        titlePenjumlahan.text = "Perkalian Campuran ${GlobalObject.perkalianMin} sampai ${GlobalObject.perkalianMax}"

        val addition1: TextView = findViewById(R.id.add1)
        val addition2: TextView = findViewById(R.id.addition2)
        val answer: EditText = findViewById(R.id.etAnswer)
        val submit: Button = findViewById(R.id.checkAnswerbtn)
        val hasil: TextView = findViewById(R.id.score)

        // Generate initial numbers
        nilai1 = (GlobalObject.perkalianMin..GlobalObject.perkalianMax).random()
        nilai2 = (1..12).random()
        addition1.text = nilai1.toString()
        addition2.text = nilai2.toString()
        hasil.text = "Skor:" + GlobalObject.score.toString()

        submit.setOnClickListener {
            val jawaban = answer.text.toString().toIntOrNull()
            if (jawaban==null) {
                wrongSound(this)
                GlobalObject.showToast(this@perkalianCustom, "Ketik Jawaban yg Benar")
            } else if (jawaban!==null){
                if (CekJawaban(nilai1, nilai2, jawaban)) {
                    GlobalObject.score += 1
                    correctSound(this)
                    GlobalObject.showToast(this@perkalianCustom, "Jawaban Benar")

                    // Regenerate new numbers only if correct
                    nilai1 = (GlobalObject.perkalianMin..GlobalObject.perkalianMax).random()
                    nilai2 = (1..12).random()
                    addition1.text = nilai1.toString()
                    addition2.text = nilai2.toString()

                    answer.text.clear()
                }
                else {
                    GlobalObject.score -= 1
                    wrongSound(this)
                    GlobalObject.showToast(this@perkalianCustom, "Jawaban Salah")
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