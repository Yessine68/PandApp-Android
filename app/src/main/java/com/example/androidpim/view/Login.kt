package com.example.androidpim.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.example.androidpim.R
import com.example.androidpim.models.User
import com.example.androidpim.models.UserLoggedIn
import com.example.androidpim.service.RetrofitApi
import com.example.androidpim.upload_post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {
    //---------------------------------------------
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var login: Button
    lateinit var remember:CheckBox
    lateinit var mSharedPref: SharedPreferences
    lateinit var     post_file: Button
    //-----------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        //--------------------------------------------
        email = findViewById(R.id.login_email)
        password = findViewById(R.id.login_password)
        login = findViewById(R.id.login_button)
        remember = findViewById(R.id.checkBox)
        mSharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE)
        println("ééééééééééééééééééééééééééééééééééééééééééééééééééééééé")
        println(mSharedPref.getString("password", "zwayten").toString())
        //--------------------------------------------
        post_file=findViewById(R.id.post_file)
        post_file.setOnClickListener {

            val intent = Intent(applicationContext, upload_post::class.java)
            startActivity(intent)
        }



        //--------------------------------------------------
        login.setOnClickListener { var user = User()
            user.email = email.text.toString()
            user.password = password.text.toString()
            val apiuser = RetrofitApi.create().userLogin(user)




            apiuser.enqueue(object: Callback<UserLoggedIn> {
                override fun onResponse(call: Call<UserLoggedIn>, response: Response<UserLoggedIn>) {
                    if(response.isSuccessful ){

                            mSharedPref.edit().apply {

                                putString("email", response.body()?.email.toString())
                                putString("password", response.body()?.password.toString())
                                if (remember.isChecked()) {
                                putBoolean("remember", true)
                                }
                                println("###########################################")
                                println(response.body())
                                println("###########################################")
                                putString("tokenUser", response.body()?.token.toString())
                                //putBoolean("session", true)
                            }.apply()

                            finish()

                        val intent = Intent(applicationContext, Home::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    } else {

                        Toast.makeText(applicationContext, "Failed to Login", Toast.LENGTH_LONG).show()

                    }
                }

                override fun onFailure(call: Call<UserLoggedIn>, t: Throwable) {

                    Toast.makeText(applicationContext, "erreur server", Toast.LENGTH_LONG).show()
                }

            })

        }
    }

}