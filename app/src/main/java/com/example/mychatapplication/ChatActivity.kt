package com.example.mychatapplication

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {
    private lateinit var chatReclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendBtn: ImageView
    private lateinit var list: ArrayList<MessageModel>
    private lateinit var messageAdapter: MessageAdapter
    var reciverRoom: String? = null
    var senderRoom: String? = null
    private lateinit var mDbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val name = intent.getStringExtra("name").toString()
        mDbRef = FirebaseDatabase.getInstance().reference
        //its a senderUID....
        val reciveruid = intent.getStringExtra("uid").toString()
        var senderUID = FirebaseAuth.getInstance().currentUser?.uid
        //
        senderRoom = reciveruid + senderUID
        reciverRoom = senderUID + reciveruid
        supportActionBar?.title = name
        chatReclerView = findViewById(R.id.chat_recler)
        messageBox = findViewById(R.id.message_box)
        sendBtn = findViewById(R.id.sendbtn)
        list = ArrayList()
        messageAdapter = MessageAdapter(this, list)
        chatReclerView.layoutManager=LinearLayoutManager(this)
        chatReclerView.adapter=messageAdapter
        messageAdapter.notifyDataSetChanged()
        //show data inside the Chat Room .......
        mDbRef.child("Chats").child(senderRoom!!).child("Messages").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for (postsnapshot in snapshot.children){
                    val message=postsnapshot.getValue(MessageModel::class.java)
                    //!! used for null safe.....
                    list.add(message!!)
                }
                 messageAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }

        })
        //adding the message to the database.....
        sendBtn.setOnClickListener {
            val message = messageBox.text.toString().trim()
            val messageObj = MessageModel(message, senderUID.toString())
            mDbRef.child("Chats").child(senderRoom!!).child("Messages").push().setValue(messageObj)
                .addOnSuccessListener {
                    mDbRef.child("Chats").child(reciverRoom!!).child("Messages").push()
                        .setValue(messageObj)
                }
        }
        messageBox.setText("")
    }
}