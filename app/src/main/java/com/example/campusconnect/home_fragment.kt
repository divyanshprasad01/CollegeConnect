package com.example.campusconnect

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class home_fragment : Fragment(R.layout.fragment_home_fragment) {

    private lateinit var createPostButton: Button
    private lateinit var postRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home_fragment, container, false)

        createPostButton = view.findViewById(R.id.createPostButton)
        postRecyclerView = view.findViewById(R.id.postRecyclerView)



        createPostButton.setOnClickListener {
            val intent = Intent(activity,NewPostActivity::class.java)
            startActivity(intent)
        }

        return view
    }





}