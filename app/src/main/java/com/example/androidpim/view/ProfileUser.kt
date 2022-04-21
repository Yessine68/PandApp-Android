package com.example.androidpim.view

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.androidpim.R
import kotlinx.android.synthetic.main.activity_sign_up_pro.*



class ProfileUser : Fragment(R.layout.activity_profile_user) {
    lateinit var mSharedPref: SharedPreferences
    lateinit var imageProfile: ImageView
    lateinit var usernameProfile: TextView
    lateinit var emailprofile: TextView
    lateinit var phoneprofile: TextView
    lateinit var editprofilebutton:Button


    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.test, parent, false)

        mSharedPref = requireActivity().getSharedPreferences("UserPref", Context.MODE_PRIVATE)

        imageProfile = view.findViewById(R.id.imageProfile)
        usernameProfile = view.findViewById(R.id.usernameProfile)
        emailprofile = view.findViewById(R.id.emailprofile)
        phoneprofile = view.findViewById(R.id.phoneprofile)
        //editprofilebutton = view.findViewById(R.id.editprofilebutton)
        editprofilebutton = view.findViewById(R.id.editprofilebutton)

        val email: String = mSharedPref.getString("email", "zwayten").toString()
        val firstName: String = mSharedPref.getString("FirstName", "zwayten").toString()
        val lastName: String = mSharedPref.getString("LastName", "zwayten").toString()
        val phone: String = mSharedPref.getString("phonenumber", "zwayten").toString()


        usernameProfile.text = firstName +" "+lastName;
        emailprofile.text = email;
        phoneprofile.text = phone


        val picStr: String = mSharedPref.getString("profilePicture", "email").toString()
        println("###############################################"+picStr)
        val ppp = "http://10.0.2.2:3000/upload/download/"+picStr
        Glide.with(this).load(Uri.parse(ppp)).into(imageProfile)

        editprofilebutton.setOnClickListener {
            var lkolPro = (activity as LkolPro)
            lkolPro.supportFragmentManager.beginTransaction().replace(R.id.frame, UserEdit()).commit()
            println("édfsgfjhsdfvsjfksfvvfghqfvsfqlfglqfhsgfsbfjsfgsjfhjdslkfgsflsfjhsklfgsks544sd52f452s4fs2f452s")
        }

        return view
    }
}