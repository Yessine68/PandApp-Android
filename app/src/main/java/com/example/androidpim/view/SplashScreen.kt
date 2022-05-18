package com.example.androidpim.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import com.example.androidpim.*
import com.example.androidpim.models.ClubLoggedIn
import com.example.androidpim.models.UserLoggedIn
import com.example.androidpim.service.RetrofitApi
import kotlinx.android.synthetic.main.fragment_onboarding3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashScreen : AppCompatActivity() {
    lateinit var mSharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        // Hide the status bar.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        actionBar?.hide()
        mSharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        //hide action bar
        supportActionBar?.hide();
        lateinit var handler: Handler
        //-------------------------------------------------------
        var user = UserLoggedIn()
        val email: String = mSharedPref.getString("email", "zwayten").toString()
        val password: String = mSharedPref.getString("password", "zwayten").toString()
        user.email = email
        user.password = password

        var club = ClubLoggedIn()
        val login: String = mSharedPref.getString("login", "zwayten").toString()
        val passwordclub: String = mSharedPref.getString("passwordclub", "zwayten").toString()
        club.login = login
        club.password = passwordclub

        var remember: Boolean = false
        var lastlogged = mSharedPref.getString("lastlogged", "aaa").toString()

        remember = mSharedPref.getBoolean("remember", false)
        print("########################################################")
        print(email);
        if(lastlogged == "user"){

        val apiuser = RetrofitApi.create().userLogin(user)


        apiuser.enqueue(object : Callback<UserLoggedIn> {
            override fun onResponse(call: Call<UserLoggedIn>, response: Response<UserLoggedIn>) {
                if (response.isSuccessful && remember == true) {
                    print(email);
                    val intent = Intent(applicationContext, LkolPro::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {
                    val intent = Intent(applicationContext, LoginPro::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)

                }
            }

            override fun onFailure(call: Call<UserLoggedIn>, t: Throwable) {

                Toast.makeText(applicationContext, "erreur server", Toast.LENGTH_LONG).show()
            }

        })
    }
        if(lastlogged == "club" && remember == true){
            val apiuserclub = RetrofitApi.create().clubLogin(club)

            apiuserclub.enqueue(object : Callback<ClubLoggedIn> {
                override fun onResponse(
                    call: Call<ClubLoggedIn>,
                    response: Response<ClubLoggedIn>
                ) {
                    if (response.isSuccessful) {
                        val intent = Intent(applicationContext, LkolPro::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    } else {
                        val intent = Intent(applicationContext, LoginPro::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                }

                override fun onFailure(call: Call<ClubLoggedIn>, t: Throwable) {

                    Toast.makeText(applicationContext, "erreur server", Toast.LENGTH_LONG).show()
                }

            })
        }

        if(lastlogged == "aaa" || remember == false){
            val intent = Intent(applicationContext, LoginPro::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }



    }






}