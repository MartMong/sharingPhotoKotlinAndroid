package com.example.mart.sharingphoto

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {


    var mAuth : FirebaseAuth? = null
    var mAuthListener : FirebaseAuth.AuthStateListener? = null
    var sharedPreferences : SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        mAuth = FirebaseAuth.getInstance()

        mAuthListener = FirebaseAuth.AuthStateListener {  }

        sharedPreferences = this.getSharedPreferences("com.example.mart.sharingphoto",android.content.Context.MODE_PRIVATE)

        var email = sharedPreferences!!.getString("email","-")
        val password = sharedPreferences!!.getString("password","-")

        mAuth!!.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener {task ->
                    if(task.isSuccessful){

                        intent = Intent(applicationContext,FeedActivity::class.java)
                        startActivity(intent)

                    }
                }.addOnFailureListener { exception ->
            Toast.makeText(applicationContext,"Please log in again",Toast.LENGTH_LONG)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


    }

    fun signIn(view : View){
        try {
            mAuth!!.signInWithEmailAndPassword(emailText.text.toString(), passwordText.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            sharedPreferences!!.edit().putString("email", emailText.text.toString()).apply()
                            sharedPreferences!!.edit().putString("password", passwordText.text.toString()).apply()

                            intent = Intent(applicationContext, FeedActivity::class.java)
                            startActivity(intent)

                        }
                    }.addOnFailureListener { exception ->
                Toast.makeText(applicationContext, exception.localizedMessage.toString(), Toast.LENGTH_LONG)
            }
        }catch(e:Exception){
            println(e.localizedMessage)
        }

    }

    fun signUp(view:View){
        try {
            mAuth!!.createUserWithEmailAndPassword(emailText.text.toString(), passwordText.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(applicationContext, "User Created", Toast.LENGTH_LONG).show()
                        }
                    }.addOnFailureListener { exception ->

                if (exception != null) {

                    Toast.makeText(applicationContext, exception.localizedMessage.toString(), Toast.LENGTH_LONG).show()

                }
            }
        }catch(e:Exception){
            println(e.localizedMessage)
        }

    }




}
