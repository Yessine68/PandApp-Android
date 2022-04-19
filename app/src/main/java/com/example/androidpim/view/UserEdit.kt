package com.example.androidpim.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.androidpim.R
import com.example.androidpim.models.User
import com.example.androidpim.models.UserLoggedIn
import com.example.androidpim.service.RetrofitApi
import kotlinx.android.synthetic.main.activity_sign_up_pro.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserEdit : Fragment(R.layout.user_edit) {
    lateinit var mSharedPref: SharedPreferences
    lateinit var imageProfile: ImageView
    lateinit var usernameProfile: TextView
    lateinit var emailprofile: EditText
    lateinit var phoneprofile: EditText
    lateinit var password1: EditText
    lateinit var password3: EditText
    lateinit var saveedit:Button

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.user_edit, parent, false)

        mSharedPref = requireActivity().getSharedPreferences("UserPref", Context.MODE_PRIVATE)

        imageProfile = view.findViewById(R.id.imageProfile)
        usernameProfile = view.findViewById(R.id.usernameProfile)
        emailprofile = view.findViewById(R.id.emailprofileedit)
        phoneprofile = view.findViewById(R.id.phoneprofileEdit)
        password1 = view.findViewById(R.id.passwordedit1)
        password3 = view.findViewById(R.id.passwordedit3)
        saveedit = view.findViewById(R.id.saveedit)


        val email: String = mSharedPref.getString("email", "zwayten").toString()
        val firstName: String = mSharedPref.getString("FirstName", "zwayten").toString()
        val lastName: String = mSharedPref.getString("LastName", "zwayten").toString()
        val phone: String = mSharedPref.getString("phonenumber", "zwayten").toString()


        usernameProfile.text = firstName +" "+lastName;
        emailprofile.setText(email);
        phoneprofile.setText(phone)


        val picStr: String = mSharedPref.getString("profilePicture", "email").toString()
        println("###############################################"+picStr)
        val ppp = "http://10.0.2.2:3000/upload/download/"+picStr
        Glide.with(this).load(Uri.parse(ppp)).into(imageProfile)
        saveedit.setOnClickListener {
            var user = User()
            if(emailprofile.text.toString() != ""){
                user.email = emailprofile.text.toString()
            }
            if( password1.text.toString()!= "" && password3.text.toString()!= ""){
                    user.password = password1.text.toString()
            }
            if(phoneprofile.text.toString() != ""){
                user.phoneNumber = phoneprofile.text.toString().toInt()
            }

            user.password = password1.text.toString()
            val apiuser = RetrofitApi.create().updateProfileUser(email,user)
            apiuser.enqueue(object : Callback<User> {
                override fun onResponse(
                    call: Call<User>,
                    response: Response<User>
                ) {
                    if (response.isSuccessful) {

                        mSharedPref.edit().apply {

                            putString("email", response.body()?.email.toString())
                            putString("password", response.body()?.password.toString())
                            putString("FirstName", response.body()?.FirstName.toString())
                            putString("profilePicture", response.body()?.profilePicture.toString())
                            putString("phonenumber", response.body()?.phoneNumber.toString())

                            println("###########################################")
                            println(response.body())
                            println("###########################################")
                            //putBoolean("session", true)
                        }.apply()



                    } else {

                       println("ggg")

                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    println("aaaaaa")

                }

            })

        }



        return view
    }
}