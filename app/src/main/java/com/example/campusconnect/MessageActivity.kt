package com.example.campusconnect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MessageActivity : AppCompatActivity() {

    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messageEditText: TextInputLayout
    private lateinit var sendButton: ImageButton
    private lateinit var messageAdaptor: MessageAdaptor
    private lateinit var messageList: ArrayList<Message>
    private lateinit var mdbRef: DatabaseReference


    lateinit var senderRoom: String
    lateinit var recieverRoom: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        chatRecyclerView = findViewById(R.id.messages)
        messageEditText = findViewById(R.id.messageBox)
        sendButton = findViewById(R.id.sendButton)

        val account = GoogleSignIn.getLastSignedInAccount(this)

        mdbRef = FirebaseDatabase.getInstance().getReference()

        messageList = ArrayList()

        messageAdaptor = MessageAdaptor(this , messageList , account?.id)


        val name = intent.getStringExtra("name")
        val recieverId = intent.getStringExtra("uid")

        supportActionBar?.title = name



        val senderId = account?.id

        senderRoom = senderId+recieverId
        recieverRoom = recieverId+senderId

        chatRecyclerView.layoutManager = LinearLayoutManager(this)


        mdbRef.child("messages").child(senderRoom).addValueEventListener(object: ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()
                for(postSnapshot in snapshot.children){
                    val messageObj = postSnapshot.getValue(Message::class.java)
                    messageList.add(messageObj!!)
                }

                messageAdaptor.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        chatRecyclerView.adapter = messageAdaptor




        sendButton.setOnClickListener{
            val message = messageEditText.editText?.text.toString()
            val messageObject = Message(message,senderId)
            messageEditText.editText?.text = null

            mdbRef.child("messages").child(senderRoom).push().setValue(messageObject)
                .addOnSuccessListener {
                    mdbRef.child("messages").child(recieverRoom).push().setValue(messageObject)
                }

        }



    }
}