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
import com.example.androidpim.models.UserLoggedIn
import com.example.androidpim.service.RetrofitApi
import com.marcoscg.dialogsheet.DialogSheet
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        lateinit var email: EditText
        lateinit var password: EditText
        lateinit var login: Button
        lateinit var signup: Button
        lateinit var remember: CheckBox
        lateinit var mSharedPref: SharedPreferences
        lateinit var resetPassword:Button

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_pro)
        supportActionBar?.hide();
        //-------------------------------------------------
        email = findViewById(R.id.login_email)
        password = findViewById(R.id.login_password)
        login = findViewById(R.id.login_button)
        signup = findViewById(R.id.signup_btn)
        remember = findViewById(R.id.checkBox)
        resetPassword = findViewById(R.id.resetPassword)

        //---------------------------------------------------
        mSharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE)
        println("ééééééééééééééééééééééééééééééééééééééééééééééééééééééé")
        println(mSharedPref.getString("password", "zwayten").toString())

        login.setOnClickListener { var user = UserLoggedIn()
            user.email = email.text.toString()
            user.password = password.text.toString()
            val apiuser = RetrofitApi.create().userLogin(user)




            apiuser.enqueue(object: Callback<UserLoggedIn> {
                override fun onResponse(call: Call<UserLoggedIn>, response: Response<UserLoggedIn>) {
                    if(response.isSuccessful ){

                        mSharedPref.edit().apply {

                            putString("email", response.body()?.email.toString())
                            putString("password", response.body()?.password.toString())
                            putString("FirstName", response.body()?.FirstName.toString())
                            putString("profilePicture", response.body()?.profilePicture.toString())
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

                        val intent = Intent(applicationContext, ProfileUser::class.java)
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

        signup.setOnClickListener {
            val intent = Intent(applicationContext, SignUpPro::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }


        val dialogSheet = DialogSheet(this@LoginPro,true) // you can also use DialogSheet2 if you want the new style
            //.setNewDialogStyle() // You can also set new style by this method, but put it on the first line
            .setTitle("Reset Password")
            .setMessage("Verification cod will be sent to the mail")
            .setSingleLineTitle(true)
            .setColoredNavigationBar(true)
            //.setButtonsColorRes(R.color.colorAccent) // You can use dialogSheetAccent style attribute instead
            .setPositiveButton(android.R.string.ok) {
                Toast.makeText(this@LoginPro, "code have been sent", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton(android.R.string.cancel)
            .setNeutralButton("Neutral")
            dialogSheet.setIconResource(R.drawable.logo)
            dialogSheet.setView(R.layout.custom_dialog_view)

        resetPassword.setOnClickListener {
            dialogSheet.show()
        }
    }
}