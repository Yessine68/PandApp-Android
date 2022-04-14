package com.example.androidpim.view

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.androidpim.R
import com.example.androidpim.models.UserLoggedIn
import com.example.androidpim.service.RetrofitApi

class ItemImage : AppCompatActivity() {
    lateinit var imageProfile:ImageView
    lateinit var usernameProfile:TextView

    lateinit var mSharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        mSharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_image)
        supportActionBar?.hide();
    //----------------
        imageProfile = findViewById(R.id.imageProfile)
        usernameProfile = findViewById(R.id.usernameProfile)

        var user = UserLoggedIn()
        val email: String = mSharedPref.getString("email", "zwayten").toString()
        val firstName: String = mSharedPref.getString("firstName", "zwayten").toString()
        val apiuser = RetrofitApi.create().getUserByEmail(email)
        val picStr: String = mSharedPref.getString("profilePicture", "email").toString()
        Glide.with(this).load(Uri.parse(picStr)).into(imageProfile)
        usernameProfile.text = firstName;

    }
}