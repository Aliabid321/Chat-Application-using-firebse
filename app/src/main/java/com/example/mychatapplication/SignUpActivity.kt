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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    lateinit var etEmail: EditText
    lateinit var etPass: EditText
    lateinit var etName: EditText
    lateinit var btnlogin: Button
    lateinit var btnSignUp: Button
    lateinit var mAuth: FirebaseAuth
    lateinit var strEmail: String
    lateinit var strName: String
    lateinit var strPass: String
    lateinit var uid:String
    private lateinit var dbref:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        mAuth = FirebaseAuth.getInstance()
        init()
    }

    private fun init() {
        uid=""
        etName = findViewById(R.id.name_et)
        etEmail = findViewById(R.id.email_et)
        etPass = findViewById(R.id.pass_et)
        btnSignUp = findViewById(R.id.btnsignup)
        btnSignUp.setOnClickListener {
            mAuth = FirebaseAuth.getInstance()
            strName = etName.text.toString().trim()
            strEmail = etEmail.text.toString().trim()
            strPass = etPass.text.toString()
            signUp(strName,strEmail,strPass,uid)
        }
    }

    fun signUp(name:String,email:String,pass:String,uid:String) {

        mAuth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = mAuth.currentUser
                    addUsertoDb(name,email,mAuth.uid.toString())
                    val int = Intent(this, MainActivity::class.java)
                    finish()
                    startActivity(int)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
    }

    private fun addUsertoDb(name: String,email:String, uid: String) {
        dbref=FirebaseDatabase.getInstance().reference
        dbref.child("Users").child(uid).setValue(User(name, email, uid))
        Toast.makeText(applicationContext,"SignUpSuccessfully",Toast.LENGTH_LONG).show()
    }
}