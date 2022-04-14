package com.example.androidpim.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.androidpim.R
import com.example.androidpim.R.layout.custom_dialog_view
import com.example.androidpim.models.UserLoggedIn
import com.example.androidpim.service.RetrofitApi

import com.marcoscg.dialogsheet.DialogSheet
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginPro : AppCompatActivity() {
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {

        lateinit var email: EditText
        lateinit var password: EditText
        lateinit var login: Button
        lateinit var signup: Button
        lateinit var remember: CheckBox
        lateinit var mSharedPref: SharedPreferences
        lateinit var resetPassword:Button
        //------code



        //code-------

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_pro)
        supportActionBar?.hide();

        var parent: ViewGroup
        var varinflater: LayoutInflater

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



        val dialogSheet = DialogSheet(this@LoginPro,true)
        dialogSheet.setView(custom_dialog_view)
        val factory = layoutInflater
        val view: View = factory.inflate(R.layout.custom_dialog_view, null)

            // you can also use DialogSheet2 if you want the new style
            //.setNewDialogStyle() // You can also set new style by this method, but put it on the first line
        dialogSheet.setTitle("Reset Password")
            .setMessage("Verification cod will be sent to the mail")
            .setSingleLineTitle(true)
            .setColoredNavigationBar(true)
            //.setButtonsColorRes(R.color.colorAccent) // You can use dialogSheetAccent style attribute instead

           /* .setPositiveButton(android.R.string.ok) {
                println("elcode+----------------"+code1.text.toString())
                Toast.makeText(this@LoginPro, "code have been sent", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton(android.R.string.cancel)
            .setNeutralButton("Neutral")
        */
            dialogSheet.setIconResource(R.drawable.logo)

        val inflatedView = dialogSheet.inflatedView
        val sendcode = inflatedView?.findViewById<Button>(R.id.sendCode)
        val customEditTextemail = inflatedView?.findViewById<EditText>(R.id.customEditTextemail)
        sendcode?.setOnClickListener {
            val apiuser = RetrofitApi.create().sendResetCode(customEditTextemail?.text.toString())

            apiuser.enqueue(object: Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if(response.isSuccessful ){

                        Toast.makeText(applicationContext, "Code Sent Successfully", Toast.LENGTH_LONG).show()


                    } else {

                        Toast.makeText(applicationContext, "Failed to Login", Toast.LENGTH_LONG).show()

                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {

                    Toast.makeText(applicationContext, "erreur server", Toast.LENGTH_LONG).show()
                }




            })




            println("elcode222+----------------"+customEditTextemail?.text.toString())
        }


        //LayoutInflater.inflate()




        resetPassword.setOnClickListener {
            dialogSheet.show()
        }
    }


}