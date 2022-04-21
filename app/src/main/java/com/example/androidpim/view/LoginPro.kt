package com.example.androidpim.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.androidpim.R
import com.example.androidpim.R.layout.*
import com.example.androidpim.models.*
import com.example.androidpim.service.RetrofitApi
import com.marcoscg.dialogsheet.DialogSheet
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle


class LoginPro : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {

        lateinit var email: EditText
        lateinit var password: EditText
        lateinit var login: Button
        lateinit var signup: Button
        lateinit var remember: CheckBox
        lateinit var mSharedPref: SharedPreferences
        lateinit var resetPassword: Button
        lateinit var loginuserr:TextView
        lateinit var loginclubb:TextView
        //------code


        //code-------

        super.onCreate(savedInstanceState)
        // Hide the status bar.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        actionBar?.hide()
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
        loginuserr= findViewById(R.id.loginuserr)
        loginclubb= findViewById(R.id.loginclubb)

         var loginAs = "user"


        loginuserr.setOnClickListener {
            loginAs = "user"
            loginuserr.background = resources.getDrawable(R.drawable.switch_trcks,null)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                loginuserr.setTextColor(resources.getColor(R.color.white,null))
            }
            loginclubb.background = null

            loginclubb.setTextColor(resources.getColor(R.color.md_red_800,null))
        }
        loginclubb.setOnClickListener {
            loginAs = "club"
            loginuserr.background = null
            loginuserr.setTextColor(resources.getColor(R.color.md_red_800,null))
            loginclubb.background = resources.getDrawable(R.drawable.switch_trcks,null)
            loginclubb.setTextColor(resources.getColor(R.color.white,null))
        }







        //---------------------------------------------------
        mSharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE)
        println("ééééééééééééééééééééééééééééééééééééééééééééééééééééééé")
        println(mSharedPref.getString("password", "zwayten").toString())

        login.setOnClickListener {


                if (loginAs == "club") {
                    var club = ClubLoggedIn()
                    club.login = email.text.toString()
                    club.password = password.text.toString()
                    val apiuser = RetrofitApi.create().clubLogin(club)

                    apiuser.enqueue(object : Callback<ClubLoggedIn> {
                        override fun onResponse(
                            call: Call<ClubLoggedIn>,
                            response: Response<ClubLoggedIn>
                        ) {
                            if (response.isSuccessful) {

                                mSharedPref.edit().apply {

                                    putString("email", response.body()?.login.toString())
                                    putString("password", response.body()?.password.toString())
                                    putString("ClubName", response.body()?.clubName.toString())
                                    putString("clubLogo", response.body()?.clubLogo.toString())
                                    putString("clubOwner", response.body()?.clubOwner.toString())
                                    putString("role", "club")
                                    putString("lastlogged", "club")
                                    if (remember.isChecked()) {
                                        putBoolean("remember", true)
                                    }
                                    println("###########################################")
                                    println(response.body())
                                    println("###########################################")
                                    putString("tokenClub", response.body()?.token.toString())
                                    //putBoolean("session", true)
                                }.apply()
                                finish()

                                val intent = Intent(applicationContext, LkolPro::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                            } else {

                                Toast.makeText(applicationContext, "Failed to Login", Toast.LENGTH_LONG)
                                    .show()

                            }
                        }

                        override fun onFailure(call: Call<ClubLoggedIn>, t: Throwable) {

                            Toast.makeText(applicationContext, "erreur server", Toast.LENGTH_LONG).show()
                        }

                    })

                } else if (loginAs == "user"){
                    var user = UserLoggedIn()
                    user.email = email.text.toString()
                    user.password = password.text.toString()
                    val apiuser = RetrofitApi.create().userLogin(user)





                    apiuser.enqueue(object : Callback<UserLoggedIn> {
                        override fun onResponse(
                            call: Call<UserLoggedIn>,
                            response: Response<UserLoggedIn>
                        ) {
                            if (response.isSuccessful) {

                                mSharedPref.edit().apply {

                                    putString("email", response.body()?.email.toString())
                                    putString("password", response.body()?.password.toString())
                                    putString("FirstName", response.body()?.FirstName.toString())
                                    putString("profilePicture", response.body()?.profilePicture.toString())
                                    putString("phonenumber", response.body()?.phoneNumber.toString())
                                    putString("role", "user")
                                    putString("lastlogged", "user")
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

                                val intent = Intent(applicationContext, LkolPro::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                            } else {

                                Toast.makeText(applicationContext, "Failed to Login", Toast.LENGTH_LONG)
                                    .show()

                            }
                        }

                        override fun onFailure(call: Call<UserLoggedIn>, t: Throwable) {

                            Toast.makeText(applicationContext, "erreur server", Toast.LENGTH_LONG).show()
                        }

                    })
                }
            }




        val dialogSheet2 = DialogSheet(this@LoginPro, true)
        dialogSheet2.setView(custom_dialog_view_singup)
        val factory2 = layoutInflater
        dialogSheet2.setTitle("Reset Password")
            .setMessage("Verification code will be sent to the mail")
            .setSingleLineTitle(true)
            .setColoredNavigationBar(true)
        val inflatedView2 = dialogSheet2.inflatedView
        val usersignup = inflatedView2?.findViewById<Button>(R.id.usersign)
        val clubignup = inflatedView2?.findViewById<Button>(R.id.clubsign)
        usersignup?.setOnClickListener {
            dialogSheet2.dismiss()
            val intent = Intent(applicationContext, OnboardingExample4Activity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        clubignup?.setOnClickListener {
            dialogSheet2.dismiss()
            val intent = Intent(applicationContext, OnboardingExample5::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }


        signup.setOnClickListener {
            dialogSheet2.show()

        }


        val dialogSheet = DialogSheet(this@LoginPro, true)
        dialogSheet.setView(custom_dialog_view)
        val factory = layoutInflater
        val view: View = factory.inflate(R.layout.custom_dialog_view, null)

        // you can also use DialogSheet2 if you want the new style
        //.setNewDialogStyle() // You can also set new style by this method, but put it on the first line
        dialogSheet.setTitle("Reset Password")
            .setMessage("Verification code will be sent to the mail")
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

        val countDownTimer = inflatedView?.findViewById<TextView>(R.id.countDownTimer)

        val code1 = inflatedView?.findViewById<EditText>(R.id.code1)
        val code2 = inflatedView?.findViewById<EditText>(R.id.code2)
        val code3 = inflatedView?.findViewById<EditText>(R.id.code3)
        val code4 = inflatedView?.findViewById<EditText>(R.id.code4)

        code1?.isEnabled = false
        code2?.isEnabled = false
        code3?.isEnabled = false
        code4?.isEnabled = false




        sendcode?.setOnClickListener {
            object : CountDownTimer(30000, 1000) {

                override fun onTick(millisUntilFinished: Long) {
                    countDownTimer?.setText("You will be eligible to resend code after " + millisUntilFinished / 1000)
                    sendcode?.isEnabled = false
                    sendcode?.setTextColor(R.color.colorPrimaryDark)
                }

                override fun onFinish() {
                    countDownTimer?.setText("")
                    sendcode?.isEnabled = true
                    sendcode?.setTextColor(R.color.md_black_1000)
                }
            }.start()
            var userReset = UserReset()
            userReset.email = customEditTextemail?.text.toString()
            mSharedPref.edit().apply {
                putString("emailreset", customEditTextemail?.text.toString())
            }.apply()
            val apiuser = RetrofitApi.create().sendResetCode(userReset)

            apiuser.enqueue(object : Callback<UserResetResponse> {
                override fun onResponse(
                    call: Call<UserResetResponse>,
                    response: Response<UserResetResponse>
                ) {

                    println("++++++++++++++response" + response.body()?.msgg.toString())
                    if (response.isSuccessful) {

                        if (response.body()?.msgg.toString() == "false") {
                            MotionToast.darkColorToast(
                                this@LoginPro,
                                "Failed ",
                                "Wrong email !",
                                MotionToastStyle.WARNING,
                                MotionToast.GRAVITY_TOP,
                                MotionToast.LONG_DURATION,
                                ResourcesCompat.getFont(
                                    this@LoginPro,
                                    www.sanju.motiontoast.R.font.helvetica_regular
                                )
                            )
                            code1?.isEnabled = false
                            code2?.isEnabled = false
                            code3?.isEnabled = false
                            code4?.isEnabled = false
                        }

                        if (response.body()?.msgg.toString() == "true") {


                            MotionToast.darkColorToast(
                                this@LoginPro,
                                "Success ",
                                "code sent!",
                                MotionToastStyle.SUCCESS,
                                MotionToast.GRAVITY_TOP,
                                MotionToast.LONG_DURATION,
                                ResourcesCompat.getFont(
                                    this@LoginPro,
                                    www.sanju.motiontoast.R.font.helvetica_regular
                                )
                            )
                            code1?.isEnabled = true
                            code2?.isEnabled = true
                            code3?.isEnabled = true
                            code4?.isEnabled = true

                            code1?.addTextChangedListener(object : TextWatcher {
                                override fun beforeTextChanged(
                                    s: CharSequence,
                                    start: Int,
                                    count: Int,
                                    after: Int
                                ) {

                                }

                                override fun onTextChanged(
                                    s: CharSequence,
                                    start: Int,
                                    before: Int,
                                    count: Int
                                ) {
                                    if (s.length == 1) {
                                        code2?.requestFocus()
                                    }
                                    code2?.addTextChangedListener(object : TextWatcher {
                                        override fun beforeTextChanged(
                                            s: CharSequence,
                                            start: Int,
                                            count: Int,
                                            after: Int
                                        ) {

                                        }

                                        override fun onTextChanged(
                                            s: CharSequence,
                                            start: Int,
                                            before: Int,
                                            count: Int
                                        ) {
                                            if (s.length == 1) {
                                                code3?.requestFocus()
                                            }
                                        }

                                        override fun afterTextChanged(s: Editable) {


                                        }
                                    })
                                    code3?.addTextChangedListener(object : TextWatcher {
                                        override fun beforeTextChanged(
                                            s: CharSequence,
                                            start: Int,
                                            count: Int,
                                            after: Int
                                        ) {

                                        }

                                        override fun onTextChanged(
                                            s: CharSequence,
                                            start: Int,
                                            before: Int,
                                            count: Int
                                        ) {
                                            if (s.length == 1) {
                                                println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                                                code4?.requestFocus()
                                            }
                                        }

                                        override fun afterTextChanged(s: Editable) {


                                        }
                                    })
                                    code4?.addTextChangedListener(object : TextWatcher {
                                        override fun beforeTextChanged(
                                            s: CharSequence,
                                            start: Int,
                                            count: Int,
                                            after: Int
                                        ) {

                                        }

                                        override fun onTextChanged(
                                            s: CharSequence,
                                            start: Int,
                                            before: Int,
                                            count: Int
                                        ) {
                                            if (s.length == 1) {
                                                println("kamalna, kahaw ahbet makech weldi")

                                                //el patch yal gennnnnnnn

                                                var check = Check()
                                                check.code = code1?.text.toString()+code2?.text.toString()+code3?.text.toString()+code4?.text.toString()
                                                val apiuser =
                                                    RetrofitApi.create().checkCode(check)
                                                apiuser.enqueue(object : Callback<CheckResponse> {
                                                    override fun onResponse(
                                                        call: Call<CheckResponse>,
                                                        response: Response<CheckResponse>
                                                    ) {
                                                        if(response.body()?.check == true){
                                                            //-----------------------------------

                                                            dialogSheet.setView(custom_dialog_view_password)


                                                            val inflatedView1 = dialogSheet.inflatedView
                                                            val save = inflatedView1?.findViewById<Button>(R.id.sendCode)
                                                            val pass1 = inflatedView1?.findViewById<EditText>(R.id.password1)
                                                            val pass2 = inflatedView1?.findViewById<EditText>(R.id.password2)

                                                            save?.setOnClickListener {
                                                                var passRes = UserResetPassword()
                                                                if(pass1?.text.toString() == pass2?.text.toString()){
                                                                    passRes.email = mSharedPref.getString("emailreset", "zwayten").toString()
                                                                    passRes.password = pass1?.text.toString()

                                                                    val apiuser =
                                                                        RetrofitApi.create().changePasswordReset(passRes)
                                                                    apiuser.enqueue(object : Callback<User> {
                                                                        override fun onResponse(
                                                                            call: Call<User>,
                                                                            response: Response<User>
                                                                        ) {
                                                                            if (response.isSuccessful) {
                                                                                dialogSheet.dismiss()
                                                                                MotionToast.darkColorToast(
                                                                                    this@LoginPro,
                                                                                    "Success ",
                                                                                    "Password reseted",
                                                                                    MotionToastStyle.SUCCESS,
                                                                                    MotionToast.GRAVITY_TOP,
                                                                                    MotionToast.LONG_DURATION,
                                                                                    ResourcesCompat.getFont(
                                                                                        this@LoginPro,
                                                                                        www.sanju.motiontoast.R.font.helvetica_regular
                                                                                    )
                                                                                )
                                                                            }
                                                                        }

                                                                        override fun onFailure(
                                                                            call: Call<User>,
                                                                            t: Throwable
                                                                        ) {

                                                                            MotionToast.darkColorToast(
                                                                                this@LoginPro,
                                                                                "Failed ",
                                                                                "Server problem",
                                                                                MotionToastStyle.ERROR,
                                                                                MotionToast.GRAVITY_TOP,
                                                                                MotionToast.LONG_DURATION,
                                                                                ResourcesCompat.getFont(
                                                                                    this@LoginPro,
                                                                                    www.sanju.motiontoast.R.font.helvetica_regular
                                                                                )
                                                                            )
                                                                        }
                                                                    })



                                                                } else {
                                                                    println("el passwords moch kifkif")
                                                                }

                                                            }




                                                        }
                                                        if(response.body()?.check == false){

                                                            val shake =
                                                                AnimationUtils.loadAnimation(
                                                                    this@LoginPro,
                                                                    R.anim.shake
                                                                )


                                                            code1?.setTextColor(R.color.colorPrimary)
                                                            code2?.setTextColor(R.color.md_red_800)
                                                            code3?.setTextColor(R.color.md_red_800)
                                                            code4?.setTextColor(R.color.md_red_800)
                                                            code1?.startAnimation(shake)
                                                            code2?.startAnimation(shake)
                                                            code3?.startAnimation(shake)
                                                            code4?.startAnimation(shake)
                                                        }

                                                    }

                                                    override fun onFailure(
                                                        call: Call<CheckResponse>,
                                                        t: Throwable
                                                    ) {
                                                        MotionToast.darkColorToast(
                                                            this@LoginPro,
                                                            "Failed ",
                                                            "Server problem",
                                                            MotionToastStyle.ERROR,
                                                            MotionToast.GRAVITY_TOP,
                                                            MotionToast.LONG_DURATION,
                                                            ResourcesCompat.getFont(
                                                                this@LoginPro,
                                                                www.sanju.motiontoast.R.font.helvetica_regular
                                                            )
                                                        )
                                                    }

                                                })


                                            }
                                        }

                                        override fun afterTextChanged(s: Editable) {


                                        }
                                    })
                                }

                                override fun afterTextChanged(s: Editable) {


                                }
                            })

                        }


                        //Toast.makeText(applicationContext, "Code Sent Successfully", Toast.LENGTH_LONG).show()


                    } else {

                        Toast.makeText(applicationContext, "Failed to Login", Toast.LENGTH_LONG)
                            .show()

                    }
                }

                override fun onFailure(call: Call<UserResetResponse>, t: Throwable) {

                    MotionToast.darkColorToast(
                        this@LoginPro,
                        "Failed ",
                        "Server problem",
                        MotionToastStyle.ERROR,
                        MotionToast.GRAVITY_TOP,
                        MotionToast.LONG_DURATION,
                        ResourcesCompat.getFont(
                            this@LoginPro,
                            www.sanju.motiontoast.R.font.helvetica_regular
                        )
                    )
                }


            })




            println("elcode222+----------------" + customEditTextemail?.text.toString())
        }


        //LayoutInflater.inflate()


        resetPassword.setOnClickListener {
            dialogSheet.show()

        }
    }


}