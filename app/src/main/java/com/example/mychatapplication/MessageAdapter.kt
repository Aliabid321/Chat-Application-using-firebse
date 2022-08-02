package com.example.mychatapplication

import android.content.Context
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val list: ArrayList<MessageModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val ITEM_RECIVE = 1;
    val ITEM_SENT = 2;
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) {
            val view: View = LayoutInflater.from(context).inflate(R.layout.recive, parent, false)
            return ReciveViewHolder(view)
        } else {
            val view: View = LayoutInflater.from(context).inflate(R.layout.send, parent, false)
            return SentViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = list[position]
        if (holder.javaClass == SentViewHolder::class.java) {
            //sent view holder is the ......
            val viewholder = holder as SentViewHolder
            viewholder.sentMessage.text = currentMessage.message

        } else {
            //recive viewholder
            val viewholder = holder as ReciveViewHolder
            viewholder.reciveMessage.text = currentMessage.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = list[position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)) {
            return ITEM_SENT
        } else {
            return ITEM_RECIVE
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sentMessage = itemView.findViewById<TextView>(R.id.text_sent_message)

    }

    class ReciveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val reciveMessage = itemView.findViewById<TextView>(R.id.text_recive_message)
    }
}