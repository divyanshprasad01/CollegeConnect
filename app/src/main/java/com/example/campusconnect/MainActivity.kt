package com.example.campusconnect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var signInClient: GoogleSignInClient
    private lateinit var bottomNavigationBar : BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationBar = findViewById(R.id.bottom_navigation)

        loadFragment(home_fragment())

        val gso  = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestProfile().build()
        signInClient = GoogleSignIn.getClient(this, gso)
        val account = GoogleSignIn.getLastSignedInAccount(this)

        if(account != null){
            val name = account.displayName
            val email = account.email
            val ProgfileImage = account.photoUrl
        }



        bottomNavigationBar.setOnItemSelectedListener {item ->
            when(item.itemId){
                R.id.item_1 -> {
                    loadFragment(home_fragment())
                    true
                }

                R.id.item_2 -> {
                    loadFragment(poll_fragment())
                    true
                }
                R.id.item_3 -> {
                    loadFragment(global_fragment())
                    true
                }
                R.id.item_4 -> {
                    loadFragment(profile_fragment())
                    true
                }

                else -> false
            }

        }

    }

    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu) : Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.notifications_icon -> {
                val intent = Intent(this,NotificationActivity::class.java)
                startActivity(intent)
            }

            R.id.chat_icon -> {
                val intent = Intent(this,ChatActivity::class.java)
                startActivity(intent)
            }

        }
        return super.onOptionsItemSelected(item)
    }
}