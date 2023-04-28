package com.example.campusconnect

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.EditText
import android.widget.ImageView



class NewPostActivity : AppCompatActivity() {

    private val PICK_IMAGE = 100

    private lateinit var ImageView: ImageView
    private lateinit var imageURI: Uri


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)

        ImageView = findViewById(R.id.createPostImage)


        ImageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(intent,PICK_IMAGE)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageURI = data?.data!!
            ImageView.setImageURI(imageURI)
        }
    }
}