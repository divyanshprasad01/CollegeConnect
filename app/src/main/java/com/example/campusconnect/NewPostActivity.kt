package com.example.campusconnect

import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask.TaskSnapshot
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.io.File


class NewPostActivity : AppCompatActivity() {

    private val PICK_IMAGE = 100

    private lateinit var ImageView: ImageView
    private lateinit var imageURI: Uri
    private lateinit var captionText: TextInputLayout
    private lateinit var uploadPost: ExtendedFloatingActionButton
    private lateinit var mDbRef: DatabaseReference
    private lateinit var storageReference: StorageReference
    lateinit var imageUrl:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)

        ImageView = findViewById(R.id.createPostImage)
        captionText = findViewById(R.id.createCaptionText)
        uploadPost = findViewById(R.id.uploadPostButton)

        storageReference = Firebase.storage.reference.child("images")

        val account = GoogleSignIn.getLastSignedInAccount(this)




        ImageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(intent,PICK_IMAGE)
        }

        uploadPost.setOnClickListener {

            val caption = captionText.editText?.text.toString()

            val filePath = storageReference.child(
                createFileName(
                    System.currentTimeMillis().toString(),
                    getFileExtension(imageURI)
                )
            )

            val progressDialogue:ProgressDialog = ProgressDialog(this)
            progressDialogue.setMessage("Uploading...")
            progressDialogue.show()

            val uploadTask = filePath.putFile(imageURI)

            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                filePath.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    imageUrl = downloadUri.toString()
                    mDbRef = FirebaseDatabase.getInstance().getReference("posts")

                    val postId = mDbRef.push().key

                    val userName = account?.displayName.toString()
                    val publisherUid = account?.id.toString()

                    val postObject = postObject(userName , publisherUid, postId, imageUrl, caption)

                    mDbRef.child(postId!!).setValue(postObject)

                    progressDialogue.dismiss()

                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)

                }
            }



        }

    }



    private fun createFileName(prefix:String , suffix:String): String{
        return "$prefix.$suffix"
    }

    private fun getFileExtension(imageURI: Uri): String {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(this.contentResolver.getType(imageURI))!!
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageURI = data?.data!!
            ImageView.setImageURI(imageURI)
        }
    }
}