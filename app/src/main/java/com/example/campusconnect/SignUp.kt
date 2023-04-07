package com.example.campusconnect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUp : AppCompatActivity() {

    private lateinit var SignUpButton: Button
    private lateinit var editName: TextInputLayout
    private lateinit var editAdminNum: TextInputLayout
    private lateinit var editEmail: TextInputLayout
    private lateinit var editPassword: TextInputLayout
    private lateinit var editCNFPassword: TextInputLayout
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        editName = findViewById(R.id.signUpName)
        editAdminNum = findViewById(R.id.signUpAdminNum)
        editEmail = findViewById(R.id.signUpEmail)
        editPassword = findViewById(R.id.signUpPassword)
        editCNFPassword = findViewById(R.id.signUpConfPassword)
        SignUpButton = findViewById(R.id.ButtonSignUpActivity)

        SignUpButton.setOnClickListener{
            signUp()
        }

    }


    private fun signUp(){
        val name = editName.editText.toString()
        val email = editEmail.editText.toString()
        val admissionNumber = editAdminNum.editText.toString()
        val Password = editPassword.editText.toString()
        val confirmPassword = editCNFPassword.editText.toString()

        database = Firebase.database.reference



    }
}