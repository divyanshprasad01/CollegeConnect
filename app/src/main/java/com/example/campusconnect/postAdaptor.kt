package com.example.campusconnect

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.loader.content.AsyncTaskLoader
import androidx.recyclerview.widget.RecyclerView
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class postAdaptor(val context:Context, val postList:ArrayList<postObject>):RecyclerView.Adapter<postAdaptor.postViewHolder>() {
    class postViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val userName = itemView.findViewById<TextView>(R.id.userPostUserName)
        val imageView = itemView.findViewById<ImageView>(R.id.postImage)
        val caption = itemView.findViewById<TextView>(R.id.captionText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): postViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_post_layout,parent,false)
        return postViewHolder(view)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: postViewHolder, position: Int) {
        val currentPost = postList[position]

        holder.userName.text = currentPost.userName
        holder.caption.text = currentPost.caption

        loadInBackGround(currentPost.imageLink , holder.imageView).execute()

    }


    class loadInBackGround(val imageLink:String? , val imageView: ImageView): AsyncTask<Void,Bitmap,Bitmap>() {

//         private val imageLink = imageLink
//         private val imageView = imageView
        override fun doInBackground(vararg p0: Void?): Bitmap {
            val imageLink = URL(imageLink)
            val bmp = BitmapFactory.decodeStream(imageLink.openConnection().getInputStream())
            return bmp
        }

        override fun onPostExecute(result: Bitmap?) {
            super.onPostExecute(result)

            imageView.setImageBitmap(result)
        }
    }

}

