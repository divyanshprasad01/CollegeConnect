package com.example.campusconnect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.FirebaseDatabaseKtxRegistrar
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChatActivity : AppCompatActivity() {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList: ArrayList<User>
    private lateinit var userAdaptor: UserAdaptor
    private lateinit var mDbRef: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        val account = GoogleSignIn.getLastSignedInAccount(this)


        userRecyclerView = findViewById(R.id.chatRecyclerView)

        mAuth = FirebaseAuth.getInstance()
        mDbRef = Firebase.database.reference


        userList = ArrayList()
        userAdaptor = UserAdaptor(this,userList )



        mDbRef.child("users").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                userList.clear()

                for(postSnapshot in snapshot.children){

                    val currentUser = postSnapshot.getValue(User::class.java)


                    if(account?.id != currentUser?.uid){
                        userList.add(currentUser!!)
                    }

                }

                userAdaptor.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })




        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.adapter = userAdaptor


    }
}