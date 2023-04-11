package com.example.campusconnect

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdaptor(val context: Context, val userList: ArrayList<User>): RecyclerView.Adapter<UserAdaptor.UserViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdaptor.UserViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_chat_adapter,parent,false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserAdaptor.UserViewHolder, position: Int) {
        val currentUser = userList[position]

        holder.userName.text = currentUser.name
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val userName = itemView.findViewById<TextView>(R.id.chatUserName)
        val userLastMessageTime = itemView.findViewById<TextView>(R.id.chatUserLastMessageTime)

    }
}