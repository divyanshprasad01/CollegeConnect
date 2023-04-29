package com.example.campusconnect

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class home_fragment : Fragment(R.layout.fragment_home_fragment) {

    private lateinit var createPostButton: Button
    private lateinit var postRecyclerView: RecyclerView
    private lateinit var listOfPost: ArrayList<postObject>
    private lateinit var mDbRef: DatabaseReference
    private lateinit var postAdaptor: postAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home_fragment, container, false)

        createPostButton = view.findViewById(R.id.createPostButton)
        postRecyclerView = view.findViewById(R.id.postRecyclerView)

        postRecyclerView.setHasFixedSize(true)
        postRecyclerView.layoutManager = LinearLayoutManager(view.context)

        listOfPost = ArrayList()

        mDbRef = FirebaseDatabase.getInstance().getReference()

        postAdaptor = postAdaptor(view.context , listOfPost)

        postRecyclerView.adapter = postAdaptor

        mDbRef.child("posts").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                listOfPost.clear()

                for(postSnapshot in snapshot.children){
                    val child = postSnapshot.getValue(postObject::class.java)
                    listOfPost.add(child!!)
                }
                postAdaptor.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        postRecyclerView.adapter = postAdaptor


        createPostButton.setOnClickListener {
            val intent = Intent(activity,NewPostActivity::class.java)
            startActivity(intent)
        }

        return view
    }





}