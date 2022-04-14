package com.example.androidpim.view

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.androidpim.R
/*
class ProfileUser : AppCompatActivity() {

    lateinit var mSharedPref: SharedPreferences
    lateinit var imageProfile: ImageView
    lateinit var usernameProfile: TextView
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_user)
        mSharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE)

        imageProfile = findViewById(R.id.imageProfile)
        usernameProfile = findViewById(R.id.usernameProfile)

        val email: String = mSharedPref.getString("email", "zwayten").toString()
        val firstName: String = mSharedPref.getString("FirstName", "zwayten").toString()


        val picStr: String = mSharedPref.getString("profilePicture", "email").toString()
        println("###############################################"+picStr)
        val ppp = "http://10.0.2.2:3000/upload/download/"+picStr
        Glide.with(this).load(Uri.parse(ppp)).into(imageProfile)
        usernameProfile.text = firstName;
    }
}
*/

class ProfileUser : Fragment(R.layout.activity_profile_user) {
    lateinit var mSharedPref: SharedPreferences
    lateinit var imageProfile: ImageView
    lateinit var usernameProfile: TextView

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_profile_user, parent, false)

        mSharedPref = requireActivity().getSharedPreferences("UserPref", Context.MODE_PRIVATE)

        imageProfile = view.findViewById(R.id.imageProfile)
        usernameProfile = view.findViewById(R.id.usernameProfile)

        val email: String = mSharedPref.getString("email", "zwayten").toString()
        val firstName: String = mSharedPref.getString("FirstName", "zwayten").toString()


        val picStr: String = mSharedPref.getString("profilePicture", "email").toString()
        println("###############################################"+picStr)
        val ppp = "http://10.0.2.2:3000/upload/download/"+picStr
        Glide.with(this).load(Uri.parse(ppp)).into(imageProfile)
        usernameProfile.text = firstName;
        return view
    }
}