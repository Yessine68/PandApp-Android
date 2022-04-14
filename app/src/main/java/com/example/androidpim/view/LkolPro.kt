package com.example.androidpim.view

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.androidpim.R


class LkolPro : AppCompatActivity() {

    lateinit var mSharedPref: SharedPreferences
    lateinit var profile_icon: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lkol_pro)
        supportActionBar?.hide();

        val homeBtn = findViewById<ImageView>(R.id.home_icon)
        val searchBtn = findViewById<ImageView>(R.id.search_icon)
        val galleryBtn = findViewById<ImageView>(R.id.add_icon)
        val favouriteBtn = findViewById<ImageView>(R.id.heart_icon)
        val profileBtn = findViewById<ImageView>(R.id.profile_icon)


        homeBtn.setOnClickListener(clickListener)
        searchBtn.setOnClickListener(clickListener)
        galleryBtn.setOnClickListener(clickListener)
        favouriteBtn.setOnClickListener(clickListener)
        profileBtn.setOnClickListener(clickListener)

        mSharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE)
        profile_icon = findViewById(R.id.profile_icon)
        val picStr: String = mSharedPref.getString("profilePicture", "email").toString()
        val ppp = "http://10.0.2.2:3000/upload/download/"+picStr
        Glide.with(this).load(Uri.parse(ppp)).into(profile_icon)
    }

    private val clickListener : View.OnClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.home_icon -> {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, HomePro()).commit()
            }
           /* R.id.search_icon -> {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, SearchFragment()).commit()
            }
            R.id.add_icon -> {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, GalleryFragment()).commit()
            }
            R.id.heart_icon -> {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, FavouriteFragment()).commit()
            }
            */

            R.id.profile_icon -> {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, ProfileUser()).commit()
            }


        }
    }
}