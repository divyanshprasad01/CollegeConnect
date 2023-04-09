package com.example.campusconnect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class LoginActivity : AppCompatActivity() {

    private val RC_SIGN_IN = 1

    private lateinit var loginButton: Button
    private lateinit var signUpButton: Button
    private lateinit var googleLogin: Button
    private lateinit var editEmail: TextInputLayout
    private lateinit var editPassword: TextInputLayout
    private lateinit var signInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: DatabaseReference






    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        val emailAccount = mAuth.currentUser
        if(account!=null || emailAccount != null) {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }


        loginButton = findViewById(R.id.loginButton)
        signUpButton = findViewById(R.id.SignUpButton)
        googleLogin = findViewById(R.id.GoogleSignUpButton)
        editEmail = findViewById(R.id.loginEmail)
        editPassword = findViewById(R.id.loginPassword)

        mAuth = FirebaseAuth.getInstance()



        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestProfile().build()
        signInClient = GoogleSignIn.getClient(this, gso)

        googleLogin.setOnClickListener{
            googleSignIn()
        }

        loginButton.setOnClickListener{
            signIn()
        }

        signUpButton.setOnClickListener{
            startActivity(Intent(this, SignUp::class.java))
        }

    }

    private fun signIn(){
        val mEmail = editEmail.editText.toString()
        val mPassword = editPassword.editText.toString()

        mAuth.signInWithEmailAndPassword(mEmail, mPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = mAuth.currentUser

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(baseContext, "Wrong Email OR Password!! Please try Again..",
                        Toast.LENGTH_LONG).show()

                }
            }

    }

    private fun googleSignIn(){
        val intent = signInClient.signInIntent
        startActivityForResult(intent,RC_SIGN_IN)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task =
                GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account : GoogleSignInAccount? = task.getResult(ApiException::class.java)

                val name: String? = account!!.displayName
                val email: String? = account.email
                val uid: String? = account.id


                addUserToDatabase(name!!, email!!, uid!!)

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)


            } catch (e: ApiException) {
                // The ApiException status code indicates the detailed failure reason.
                // Please refer to the GoogleSignInStatusCodes class reference for more information.
                Toast.makeText(this, "SOME ERROR OCCURED!! PLEASE TRY AGAIN", Toast.LENGTH_LONG).show()

            }
        }
    }


    private fun addUserToDatabase(name: String, email: String, uid: String){
        database = Firebase.database.reference

        database.child("users").child(uid).setValue(User(name,email,uid))

    }



}