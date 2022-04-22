package com.example.androidpim.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.androidpim.R
import com.example.androidpim.fragments.EventFragment


class LkolPro : AppCompatActivity() {

    lateinit var mSharedPref: SharedPreferences
    lateinit var profile_icon: ImageView
    lateinit var  camera_img:ImageButton
    lateinit var frag:Fragment
    lateinit var loggedAs:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Hide the status bar.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        actionBar?.hide()
        setContentView(R.layout.activity_lkol_pro)
        supportActionBar?.hide();
        frag = EventFragment()

        val homeBtn = findViewById<ImageView>(R.id.home_icon)
        val searchBtn = findViewById<ImageView>(R.id.search_icon)
        val galleryBtn = findViewById<ImageView>(R.id.add_icon)
        val favouriteBtn = findViewById<ImageView>(R.id.heart_icon)
        val profileBtn = findViewById<ImageView>(R.id.profile_icon)
        val camera_img = findViewById<ImageView>(R.id.camera_img)


        homeBtn.setOnClickListener(clickListener)
        searchBtn.setOnClickListener(clickListener)
        galleryBtn.setOnClickListener(clickListener)
        favouriteBtn.setOnClickListener(clickListener)
        profileBtn.setOnClickListener(clickListener)

        mSharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE)
        profile_icon = findViewById(R.id.profile_icon)
        val picStr: String = mSharedPref.getString("profilePicture", "email").toString()
         loggedAs = mSharedPref.getString("lastlogged", "user").toString()
        val ppp = "http://10.0.2.2:3000/upload/download/"+picStr
        Glide.with(this).load(Uri.parse(ppp)).into(profile_icon)
        supportFragmentManager.beginTransaction().replace(R.id.frame, HomePro()).commit()
        supportFragmentManager.beginTransaction().replace(R.id.frameEvent, frag).commit()

        camera_img.setOnClickListener{
            mSharedPref.edit().clear().apply()
            val intent = Intent(applicationContext, LoginPro::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private val clickListener : View.OnClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.home_icon -> {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, HomePro()).commit()
                getSupportFragmentManager().beginTransaction().replace(R.id.frameEvent, frag).commit()
            }
            R.id.search_icon -> {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, EventFragment()).commit()
                getSupportFragmentManager().beginTransaction().remove(frag).commit()

            }
            /*
            R.id.add_icon -> {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, GalleryFragment()).commit()
            }
            R.id.heart_icon -> {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, FavouriteFragment()).commit()
            }
            */

            R.id.profile_icon -> {
                if(loggedAs == "user") {
                    getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, ProfileUser()).commit()
                }
                if(loggedAs == "club") {
                    getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, ProfileClub()).commit()
                }
                getSupportFragmentManager().beginTransaction().remove(frag).commit()
            }


        }
    }
}