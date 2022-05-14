package com.example.androidpim.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.androidpim.R
import com.example.androidpim.fragments.DocumentsFragment
import com.example.androidpim.models.User
import com.example.androidpim.models.UserLoggedIn
import com.example.androidpim.service.BASE_URL
import com.example.androidpim.service.RetrofitApi
import com.marcoscg.dialogsheet.DialogSheet
import kotlinx.android.synthetic.main.activity_sign_up_pro.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileUser : Fragment(R.layout.activity_profile_user) {
    lateinit var mSharedPref: SharedPreferences
    lateinit var imageProfile: ImageView
    lateinit var usernameProfile: TextView
    lateinit var emailprofile: TextView
    lateinit var phoneprofile: TextView
    lateinit var editprofilebutton:Button
    lateinit var documentsrequest:Button


    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.test, parent, false)

        mSharedPref = requireActivity().getSharedPreferences("UserPref", Context.MODE_PRIVATE)

        imageProfile = view.findViewById(R.id.imageProfile)
        usernameProfile = view.findViewById(R.id.usernameProfile)
        emailprofile = view.findViewById(R.id.emailprofile)
        phoneprofile = view.findViewById(R.id.phoneprofile)
        //editprofilebutton = view.findViewById(R.id.editprofilebutton)
        editprofilebutton = view.findViewById(R.id.editprofilebutton)
        documentsrequest = view.findViewById(R.id.documentsrequest)

        val email: String = mSharedPref.getString("email", "zwayten").toString()
        val firstName: String = mSharedPref.getString("FirstName", "zwayten").toString()
        val lastName: String = mSharedPref.getString("LastName", "zwayten").toString()
        val phone: String = mSharedPref.getString("phonenumber", "zwayten").toString()


        usernameProfile.text = firstName +" "+lastName;
        emailprofile.text = email;
        phoneprofile.text = phone


        var userInstance = UserLoggedIn()
        val apiuser = email?.let { RetrofitApi.create().getUserByEmail(it) }
        apiuser?.enqueue(object: Callback<List<UserLoggedIn>> {


            override fun onResponse(
                call: Call<List<UserLoggedIn>>,
                response: Response<List<UserLoggedIn>>
            ) {
                if(response.body()!!.size!=0){
                    //--------login
                    userInstance = response.body()!![0]
                    val picStr: String = mSharedPref.getString("profilePicture", "email").toString()
            val testgolgol = picStr.contains("googleusercontent.com")
                    if(testgolgol == false){

                        println("###############################################"+userInstance.profilePicture)
                        val ppp = BASE_URL +"upload/download/"+userInstance.profilePicture
                        if (parent != null) {
                            Glide.with(parent.context).load(Uri.parse(ppp)).into(imageProfile)
                        }
                    }
                    else {
                        if (parent != null) {
                            Glide.with(parent.context).load(Uri.parse(userInstance.profilePicture)).into(imageProfile)
                        }
                    }

                    //----------------
                    println("user already exist")
                }

            }

            override fun onFailure(call: Call<List<UserLoggedIn>>, t: Throwable) {
                println("fchelna")
            }
        })





        editprofilebutton.setOnClickListener {
            var lkolPro = (activity as LkolPro)
            lkolPro.supportFragmentManager.beginTransaction().replace(R.id.frame, UserEdit()).commit()
            println("édfsgfjhsdfvsjfksfvvfghqfvsfqlfglqfhsgfsbfjsfgsjfhjdslkfgsflsfjhsklfgsks544sd52f452s4fs2f452s")
        }

        documentsrequest.setOnClickListener {
            var lkolPro = (activity as LkolPro)
            lkolPro.supportFragmentManager.beginTransaction().replace(R.id.frame, DocumentsFragment()).commit()
        }

        return view
    }
}