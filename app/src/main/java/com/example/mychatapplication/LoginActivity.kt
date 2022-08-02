package com.example.mychatapplication

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var etEmail: EditText
    lateinit var etPass: EditText
    lateinit var btnlogin: Button
    lateinit var btnSignUp: Button
    lateinit var mAuth:FirebaseAuth
    lateinit var strEmail:String
    lateinit var strPass:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        init()
    }

    fun init() {
        mAuth= FirebaseAuth.getInstance()
        etEmail = findViewById(R.id.email_et)
        etPass = findViewById(R.id.pass_et)
        btnlogin = findViewById(R.id.btnlogin)
        btnSignUp = findViewById(R.id.btnsignup)
        btnSignUp.setOnClickListener {
            val intent=Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }
        btnlogin.setOnClickListener {
            signIn()
        }
    }
    private fun signIn(){
        strEmail=etEmail.text.toString().trim()
        strPass=etPass.text.toString().trim()
        mAuth.signInWithEmailAndPassword(strEmail, strPass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = mAuth.currentUser
                    val int = Intent(this, MainActivity::class.java)
                    finish()
                    startActivity(int)
//                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                   // updateUI(null)
                }
            }

    }
}