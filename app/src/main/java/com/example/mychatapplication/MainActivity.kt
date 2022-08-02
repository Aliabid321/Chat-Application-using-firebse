package com.example.mychatapplication

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class MainActivity : AppCompatActivity() {
    private lateinit var userRecler: RecyclerView
    private lateinit var userList: ArrayList<User>
    private lateinit var adapter: UserAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userRecler = findViewById(R.id.user_recler)
        init()
    }

    private fun init() {


        getUsers()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.usermenu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //return super.onOptionsItemSelected(item)
        if (item.itemId == R.id.logout) {
            mAuth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            finish()
            startActivity(intent)
            return true
        }
        return true
    }
    private fun getUsers(){
        mAuth = FirebaseAuth.getInstance()
        mDbRef=FirebaseDatabase.getInstance().reference
        userList = ArrayList()
        adapter = UserAdapter(this, userList)

        userRecler.layoutManager=LinearLayoutManager(this)
        userRecler.adapter=adapter
        // Read from the database
        mDbRef.child("Users").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                userList.clear()
                for (usersnapshot in snapshot.children){
                    val currentUser=usersnapshot.getValue(User::class.java)
                    if (currentUser != null) {
                        userList.add(currentUser)
                    }
                }
                adapter.notifyDataSetChanged()
//                val value = snapshot.getValue<String>()
//                Log.d(TAG, "Value is: " + value)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }

        })
    }
}