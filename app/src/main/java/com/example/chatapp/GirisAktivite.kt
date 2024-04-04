package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.aktivite_giris.*

class GirisAktivite : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.aktivite_giris)

        auth = FirebaseAuth.getInstance()
        val guncelKullanici = auth.currentUser

        if (guncelKullanici != null)
        {

            val intent = Intent(this,AnasayfaAktivite::class.java)
            startActivity(intent)
            finish()
        }

    }
    fun girisyap(view: View) {


        auth.signInWithEmailAndPassword(EmailText.text.toString(), PasswordText.text.toString())
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    val guncelKullanici = auth.currentUser?.email.toString()
                    Toast.makeText(this, "Hoşgeldin:${guncelKullanici}", Toast.LENGTH_LONG)
                        .show()

                    val intent = Intent(this, AnasayfaAktivite::class.java)
                    startActivity(intent)
                    finish()
                }
            }

    }


    fun kayitol(view: View) {
        val email = EmailText.text.toString()
        val sifre = PasswordText.text.toString()
        auth.createUserWithEmailAndPassword(email, sifre).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val intent = Intent(this, AnasayfaAktivite::class.java)
                startActivity(intent)
                finish()
            }
        }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    applicationContext,
                    exception.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()
            }
    }

}
