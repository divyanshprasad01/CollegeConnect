package com.example.campusconnect

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class postAdaptor(val context:Context, val postList:ArrayList<postObject>):RecyclerView.Adapter<postAdaptor.postViewHolder>() {
    class postViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val userName = itemView.findViewById<TextView>(R.id.userPostUserName)
        val imageView = itemView.findViewById<ImageView>(R.id.postImage)
        val caption = itemView.findViewById<TextView>(R.id.captionText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): postViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_post_layout,parent,false)
        return postViewHolder(view)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: postViewHolder, position: Int) {
        val currentPost = postList[position]

        holder.userName.text = currentPost.userName
        holder.caption.text = currentPost.caption
        BackgroundImageLoader(currentPost.imageLink!! , holder.imageView)
    }

}