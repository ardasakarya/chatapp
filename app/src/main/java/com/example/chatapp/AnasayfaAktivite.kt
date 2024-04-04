package com.example.chatapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.text.set
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firestore.v1.FirestoreGrpc.FirestoreImplBase
import kotlinx.android.synthetic.main.activity_anasayfa_aktivite.*



class AnasayfaAktivite : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseFirestore
    private lateinit var recyclerAdapter: AnaMenuRecyclerAdapter
    private var postListesi = arrayListOf<Post>()
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anasayfa_aktivite)

        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

      recyclerAdapter = AnaMenuRecyclerAdapter()
mesajGoster()
       recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

fun mesajGoster()
{
    database.collection("chats").orderBy("date", Query.Direction.ASCENDING)
        .addSnapshotListener { value, error ->

            if (error != null) {
                Toast.makeText(
                    applicationContext,
                    error.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()
            } else {
                if (value != null) {
                    if (value.isEmpty) {
                        Toast.makeText(applicationContext, "Mesaj Yok", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        val documents = value.documents
                        postListesi.clear()
                        for (document in documents) {
                            val text = document.get("text") as String
                            val user = document.get("user") as String
                            val chat = Post(user,text)
                            postListesi.add(chat)
                            recyclerAdapter.chats=postListesi
                        }
                    }
                    recyclerAdapter.notifyDataSetChanged()
                }
            }
        }
}

    fun paylas(view: View) {
            auth.currentUser?.let {

                val user = it.email
                var chatText = MesajText.text.toString()
                val date = FieldValue.serverTimestamp()

                val dataMap = HashMap<String, Any>()
                dataMap.put("text", chatText)
                dataMap.put("user", user!!)
                dataMap.put("date", date)
                database.collection("chats").add(dataMap).addOnSuccessListener {

                    MesajText.setText("")
                }.addOnFailureListener { exception ->
                    Toast.makeText(
                        applicationContext,
                        exception.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()
                    MesajText.setText("")
                }

            }
            mesajGoster()
                }



        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            val menuInflater = menuInflater
            menuInflater.inflate(R.menu.secenekler_menu, menu)
            return super.onCreateOptionsMenu(menu)
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            if (item.itemId == R.id.cikisyap) {
                auth.signOut()
                val intent = Intent(this, GirisAktivite::class.java)
                startActivity(intent)
                finish()
            }

            return super.onOptionsItemSelected(item)
        }

}