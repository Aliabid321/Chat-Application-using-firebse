package com.example.mychatapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class UserAdapter(val context: Context, val userlist: List<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.userlayout, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userlist[position]
        holder.textname.text = currentUser.name
        holder.itemView.setOnClickListener{
         val intent=Intent(context,ChatActivity::class.java)
            intent.putExtra("name",userlist[position].name)
            intent.putExtra("uid",userlist[position].uid)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userlist.size
    }

    class UserViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val textname: TextView = itemview.findViewById(R.id.name_text)


    }
}