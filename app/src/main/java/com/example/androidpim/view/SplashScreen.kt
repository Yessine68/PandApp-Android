package com.example.androidpim.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import com.example.androidpim.R
import com.example.androidpim.models.UserLoggedIn
import com.example.androidpim.service.RetrofitApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashScreen : AppCompatActivity() {
    lateinit var mSharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
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
        var remember: Boolean = false
        user.email = email
        user.password = password
        remember = mSharedPref.getBoolean("remember", false)
        print("########################################################")
        print(email);
        val apiuser = RetrofitApi.create().userLogin(user)

        apiuser.enqueue(object: Callback<UserLoggedIn> {
            override fun onResponse(call: Call<UserLoggedIn>, response: Response<UserLoggedIn>) {
                if(response.isSuccessful && remember == true){
                    print(email);
                    val intent = Intent(applicationContext, LkolPro::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {

                    val intent = Intent(applicationContext, LkolPro::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)

                }
            }

            override fun onFailure(call: Call<UserLoggedIn>, t: Throwable) {

                Toast.makeText(applicationContext, "erreur server", Toast.LENGTH_LONG).show()
            }

        })



    }






}