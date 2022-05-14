package com.example.androidpim.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.androidpim.R
import com.example.androidpim.fragments.EventFragment
import com.example.androidpim.fragments.ListOfChat
import com.example.androidpim.fragments.ListOfCourses
import com.example.androidpim.fragments.lostfoundfrag
import com.example.androidpim.service.BASE_URL

class LkolPro : AppCompatActivity() {
    lateinit var mSharedPref: SharedPreferences
    lateinit var profile_icon: ImageView
    lateinit var frag:Fragment
    lateinit var lostfound:Fragment
    lateinit var loggedAs:String
    lateinit var send_img:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Hide the status bar.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        actionBar?.hide()
        setContentView(R.layout.activity_lkol_pro)
        supportActionBar?.hide();
        frag = EventFragment()
        lostfound =lostfoundfrag()
         send_img = findViewById<ImageButton>(R.id.send_img)

        val homeBtn = findViewById<ImageView>(R.id.home_icon)
        val searchBtn = findViewById<ImageView>(R.id.search_icon)
        val galleryBtn = findViewById<ImageView>(R.id.add_icon)
        val favouriteBtn = findViewById<ImageView>(R.id.heart_icon)
        val profileBtn = findViewById<ImageView>(R.id.profile_icon)
        val camera_img = findViewById<ImageView>(R.id.camera_img)
        homeBtn.setOnClickListener(clickListener)
        searchBtn.setOnClickListener(clickListener)
        galleryBtn.setOnClickListener(clickListener)
        favouriteBtn.setOnClickListener(clickListener)
        profileBtn.setOnClickListener(clickListener)
        send_img.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.frame, ListOfChat()).commit()
            supportFragmentManager.beginTransaction().remove(frag).commit()
        }
        mSharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE)
        profile_icon = findViewById(R.id.profile_icon)
        var picStr: String = ""
        if(mSharedPref.getString("lastlogged", "club").toString() == "club")
        {
             picStr = mSharedPref.getString("clubLogo", "email").toString()
        }
         loggedAs = mSharedPref.getString("lastlogged", "user").toString()
        if(loggedAs == "user"){
            picStr =  mSharedPref.getString("profilePicture", "email").toString()
        }
        val ppp = BASE_URL +"upload/download/"+picStr
        Glide.with(this).load(Uri.parse(ppp)).into(profile_icon)
        supportFragmentManager.beginTransaction().replace(R.id.frame, HomePro()).commit()
        supportFragmentManager.beginTransaction().replace(R.id.frameEvent, frag).commit()

        camera_img.setOnClickListener{
            mSharedPref.edit().clear().apply()
            val intent = Intent(applicationContext, LoginPro::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private val clickListener : View.OnClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.home_icon -> {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, HomePro()).commit()
                getSupportFragmentManager().beginTransaction().replace(R.id.frameEvent, frag).commit()
                getSupportFragmentManager().beginTransaction().remove(lostfound).commit()
            }
            R.id.search_icon -> {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, ListOfCourses()).commit()
                getSupportFragmentManager().beginTransaction().remove(frag).commit()
                getSupportFragmentManager().beginTransaction().remove(lostfound).commit()


            }

            R.id.add_icon -> {
                val intent = Intent(applicationContext, Scc::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }


            R.id.heart_icon -> {
                lostfound = lostfoundfrag()
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, lostfound).commit()
                getSupportFragmentManager().beginTransaction().remove(frag).commit()

            }
            R.id.profile_icon -> {
                if(loggedAs == "user") {
                    getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, ProfileUser()).commit()
                }
                if(loggedAs == "club") {
                    getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, ProfileClub()).commit()
                }
                getSupportFragmentManager().beginTransaction().remove(frag).commit()
                getSupportFragmentManager().beginTransaction().remove(lostfound).commit()

            }


        }
    }
}