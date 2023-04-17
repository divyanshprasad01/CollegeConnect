package com.example.campusconnect

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth

class MessageAdaptor(val context: Context, val messageList: ArrayList<Message>, val currentLoggedInUser: String?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_SENT = 1
    val ITEM_RECIEVED = 2

    class sentViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        val sentMessage = itemView.findViewById<TextView>(R.id.sentMessage)

    }

    class recievedViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val recievedMessage = itemView.findViewById<TextView>(R.id.recievedMessage)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        if(viewType == 1){
            val view:View = LayoutInflater.from(context).inflate(R.layout.message_sent_layout,parent,false)
            return sentViewHolder(view)
        }else{
            val view:View = LayoutInflater.from(context).inflate(R.layout.message_recieved_layout,parent,false)
            return sentViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]

        if(currentLoggedInUser.equals(currentMessage.senderId)){
            return ITEM_SENT
        }else{
            return ITEM_RECIEVED
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentMessage = messageList[position]

        if(holder.javaClass == sentViewHolder::class.java){
            val viewHolder = holder as sentViewHolder

            holder.sentMessage.text = currentMessage.message

        }else{
            val viewHolder = holder as recievedViewHolder

            holder.recievedMessage.text = currentMessage.message
        }
    }
}