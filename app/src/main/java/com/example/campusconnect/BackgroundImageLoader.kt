package com.example.campusconnect

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.ImageView
import androidx.loader.content.AsyncTaskLoader
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class BackgroundImageLoader(uri: String, ImageView: ImageView): AsyncTask<Void,Void,Bitmap>() {
    private var url:String = uri
    private var ImageView:ImageView = ImageView


    override fun doInBackground(vararg p0: Void?):Bitmap? {
        try {
            val urlConnection: URL = URL(url)
            val connecton: HttpURLConnection = urlConnection.openConnection() as HttpURLConnection
            connecton.doInput
            connecton.connect()
            val input:InputStream = connecton.inputStream
            val bitMap:Bitmap = BitmapFactory.decodeStream(input)
            return bitMap
        }catch (e:Exception){
            e.printStackTrace()
        }
        return null
    }

    override fun onPostExecute(result: Bitmap?) {
        super.onPostExecute(result)
        ImageView.setImageBitmap(result)
    }
}