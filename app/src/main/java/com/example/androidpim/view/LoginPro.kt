package com.example.androidpim.view

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.*
import android.os.StrictMode.ThreadPolicy
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition

import com.example.androidpim.BuildConfig
import com.example.androidpim.R
import com.example.androidpim.R.layout.*
import com.example.androidpim.models.*
import com.example.androidpim.service.RetrofitApi
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.marcoscg.dialogsheet.DialogSheet
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.io.File
import java.io.InputStream
import java.net.URL


class LoginPro : AppCompatActivity() {

    lateinit var dialogCOmpleteUser: DialogSheet
    lateinit var selectedImageUri:Uri
    lateinit var directory: File
     var testi:String = "notOk"
    private var imageUrl = "https://i.imgur.com/yc3CbKN.jpg"
/*
    @SuppressLint("Range")
    private fun downloadImage(url: String) {
         directory = File(Environment.DIRECTORY_PICTURES)

        if (!directory.exists()) {
            directory.mkdirs()
        }

        val downloadManager = this.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val downloadUri = Uri.parse(url)

        val request = DownloadManager.Request(downloadUri).apply {
            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(url.substring(url.lastIndexOf("/") + 1))
                .setDescription("")
                .setDestinationInExternalPublicDir(
                    directory.toString(),
                    url.substring(url.lastIndexOf("/") + 1)
                )
        }
        println( url.substring(url.lastIndexOf("/") + 1))
        val downloadId = downloadManager.enqueue(request)

        println("el fichier "+request.)
        println("el directory : "+directory.toString())
        val query = DownloadManager.Query().setFilterById(downloadId)
        Thread(Runnable {
            var downloading = true
            while (downloading) {
                val cursor: Cursor = downloadManager.query(query)
                cursor.moveToFirst()
                if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                    downloading = false
                }

                cursor.close()
            }
        }).start()
    }
    */

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
        lateinit var googlebuttonLogin: Button




        super.onCreate(savedInstanceState)
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
         directory = File(Environment.DIRECTORY_PICTURES)
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
        googlebuttonLogin = findViewById(R.id.googlebuttonLogin)




        //------code


        oneTapClient = Identity.getSignInClient(this)
        signUpRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(BuildConfig.WEB_CLIENT_ID)
                    // Show all accounts on the device.
                    .setFilterByAuthorizedAccounts(false)
                    .build())
            .build()
        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)

                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(BuildConfig.WEB_CLIENT_ID)
                    // Show all accounts on the device.
                    .setFilterByAuthorizedAccounts(false)
                    .build())
            .build()

        googlebuttonLogin.setOnClickListener { displaySignIn() }

        //code-------

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

                                    putString("login", response.body()?.login.toString())
                                    putString("passwordclub", response.body()?.password.toString())
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
                            println("loggedin"+response.body().toString())
                            if (response.isSuccessful) {
                                mSharedPref.edit().apply {
                                    putString("className", response.body()?.className.toString())
                                    putString("email", response.body()?.email.toString())
                                    putString("password", response.body()?.password.toString())
                                    putString("FirstName", response.body()?.FirstName.toString())
                                    putString("profilePicture", response.body()?.profilePicture.toString())
                                    putString("phonenumber", response.body()?.phoneNumber.toString())
                                    putString("identifiant", response.body()?.identifant.toString())
                                    putString("id", response.body()?._id.toString())
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




    private var oneTapClient: SignInClient? = null
    private var signUpRequest: BeginSignInRequest? = null
    private var signInRequest: BeginSignInRequest? = null


    private val oneTapResult = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()){ result ->
        try {
            lateinit var mSharedPref: SharedPreferences
            mSharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE)
            val credential = oneTapClient?.getSignInCredentialFromIntent(result.data)
            val idToken = credential?.googleIdToken
            when {
                idToken != null -> {

                    val displayName = credential?.displayName
                    val email = credential?.id
                    val prp = credential?.profilePictureUri
                    val imageUrl = credential?.profilePictureUri.toString()
                    println("el url "+prp!!)
                    // Got an ID token from Google. Use it to authenticate
                    // with your backend.
                    val msg = "idToken: $idToken"
                    val nameMsg = "displaName :$displayName"
                    val emailMsg = "displaName :$email"

                  /*  AltexImageDownloader.writeToDisk(applicationContext,
                        credential?.profilePictureUri.toString(), "IMAGES");

                   */
                    var userInstance = UserLoggedIn()
                    var userInstancesignup = User()
                    Log.d("one tap", msg)
                    println("el nameMsg:   "+nameMsg)
                    println("el email:   "+emailMsg)
                    userInstance.email = email
                    userInstance.FirstName = displayName
                    userInstancesignup.email = email
                    userInstancesignup.FirstName = displayName
                    userInstancesignup.profilePicture = credential?.profilePictureUri.toString()
                //---------test if exist
                    val apiuser = email?.let { RetrofitApi.create().getUserByEmail(it) }
                    apiuser?.enqueue(object: Callback<List<UserLoggedIn>> {


                        override fun onResponse(
                            call: Call<List<UserLoggedIn>>,
                            response: Response<List<UserLoggedIn>>
                        ) {
                            if(response.body()!!.size!=0){
                                //--------login
                                userInstance = response.body()!![0]
                                val apiuserrr = RetrofitApi.create().userLogin(userInstance)





                                apiuserrr.enqueue(object : Callback<UserLoggedIn> {
                                    override fun onResponse(
                                        call: Call<UserLoggedIn>,
                                        response: Response<UserLoggedIn>
                                    ) {
                                        if (response.isSuccessful) {

                                            mSharedPref.edit().apply {

                                                putString("email", userInstance.email.toString())
                                                putString("password", userInstance.password.toString())
                                                putString("FirstName", userInstance.FirstName.toString())
                                                putString("profilePicture", userInstance.profilePicture.toString())
                                                putString("phonenumber", userInstance.phoneNumber.toString())
                                                putString("identifiant", userInstance.identifant.toString())
                                                putString("id", userInstance._id.toString())
                                                putString("role", "user")
                                                putString("lastlogged", "user")

                                                println("###########################################")
                                                println(response.body())
                                                println("###########################################")
                                                putString("tokenUser", userInstance.token.toString())
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
                                    //----------------
                                println("user already exist")
                            }
                            else {
                                //----------------------------------------------
                                //------- complete user
                                val dialogCOmpleteUser = DialogSheet(this@LoginPro, true)
                                dialogCOmpleteUser.setView(finish_registration_user)
                                val inflatedView2 = dialogCOmpleteUser.inflatedView



                                val identifantgoogle = inflatedView2?.findViewById<EditText>(R.id.identifantgoogle)
                                val passwordgoogle = inflatedView2?.findViewById<EditText>(R.id.passwordgoogle)
                                val password2google = inflatedView2?.findViewById<EditText>(R.id.password2google)
                                val phonegoogle = inflatedView2?.findViewById<EditText>(R.id.phonegoogle)
                                val classNamegoogle = inflatedView2?.findViewById<EditText>(R.id.classNamegoogle)
                                val descriptiongoogle = inflatedView2?.findViewById<EditText>(R.id.descriptiongoogle)
                                val imageProfilegoogle = inflatedView2?.findViewById<ImageView>(R.id.imageProfilegoogle)
                                val savegoogle = inflatedView2?.findViewById<Button>(R.id.savegoogle)

                                Glide.with(applicationContext)
                                    .asBitmap()
                                    .load(credential!!.profilePictureUri.toString())
                                    .into(object : CustomTarget<Bitmap>(){
                                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                                            imageProfilegoogle?.setImageBitmap(resource)
                                            println("el ressource 7adhret"+ resource.toString())
                                        }
                                        override fun onLoadCleared(placeholder: Drawable?) {
                                            println("jawek behy")
                                        }
                                    })




                                savegoogle?.setOnClickListener {
                                    userInstancesignup.identifant = identifantgoogle?.text.toString()
                                    userInstancesignup.password = passwordgoogle?.text.toString()
                                    userInstancesignup.phoneNumber = phonegoogle?.text.toString().toInt()
                                    userInstancesignup.className = classNamegoogle?.text.toString()
                                    userInstancesignup.description = descriptiongoogle?.text.toString()
                                    userInstancesignup.LastName = ""

                                    //ne9s el image
                                    ////###########################################################################





                                    //val selectedImageUri: Uri?= Uri.parse("https://lh3.googleusercontent.com/a-/AOh14GgTQqjVQuKMLEwPs2ThTCn1sOQbbmsOOb4C0-sREw=s96-c")





                                        val emptyString = ""
                                        val apiInterface = RetrofitApi.create()

                                            apiInterface.usergooglesignup(userInstancesignup).enqueue(object:
                                                Callback<User> {
                                                override fun onResponse(
                                                    call: Call<User>,
                                                    response: Response<User>
                                                ) {
                                                    if(response.isSuccessful){
                                                        Log.i("onResponse goooood", response.body().toString())

                                                    } else {
                                                        Log.i("OnResponse not good", response.body().toString())
                                                    }
                                                }

                                                override fun onFailure(call: Call<User>, t: Throwable) {

                                                    println("noooooooooooooooooo")
                                                }

                                            })


                                    ////###########################################################################


                                }


                                //-------------------------------------------------
                                dialogCOmpleteUser.show()
                            }
                        }

                        override fun onFailure(call: Call<List<UserLoggedIn>>, t: Throwable) {
                            println("fchelna")
                        }
                    })

                    //--------------



                }
                else -> {
                    // Shouldn't happen.
                    Log.d("one tap", "No ID token!")


                }
            }
        } catch (e: ApiException) {
            when (e.statusCode) {
                CommonStatusCodes.CANCELED -> {
                    Log.d("one tap", "One-tap dialog was closed.")
                    // Don't re-prompt the user.


                }
                CommonStatusCodes.NETWORK_ERROR -> {
                    Log.d("one tap", "One-tap encountered a network error.")
                    // Try again or just ignore.


                }
                else -> {
                    Log.d("one tap", "Couldn't get credential from result." +
                            " (${e.localizedMessage})")



                }
            }
        }
    }
    private fun displaySignIn(){
        oneTapClient?.beginSignIn(signInRequest!!)
            ?.addOnSuccessListener(this) { result ->
                try {
                    val ib = IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                    oneTapResult.launch(ib)
                } catch (e: IntentSender.SendIntentException) {

                    Log.e("btn click", "Couldn't start One Tap UI: ${e.localizedMessage}")
                    Log.e("btn click", "problerm")
                }
            }
            ?.addOnFailureListener(this) { e ->
                // No Google Accounts found. Just continue presenting the signed-out UI.
                displaySignUp()
                Log.d("btn click", e.localizedMessage!!)
                Log.e("btn click", "problerm 22222")

            }
    }

    private fun displaySignUp() {
        oneTapClient?.beginSignIn(signUpRequest!!)
            ?.addOnSuccessListener(this) { result ->
                try {
                    val ib = IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                    oneTapResult.launch(ib)
                } catch (e: IntentSender.SendIntentException) {
                    Log.e("btn click", "Couldn't start One Tap UI: ${e.localizedMessage}")
                }
            }
            ?.addOnFailureListener(this) { e ->
                // No Google Accounts found. Just continue presenting the signed-out UI.

                Log.d("ezzeeeebi", e.localizedMessage!!)

            }


    }

    companion object {
        private const val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1
    }
}