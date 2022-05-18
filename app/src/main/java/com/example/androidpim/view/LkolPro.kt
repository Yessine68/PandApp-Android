package com.example.androidpim.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.androidpim.R
import com.example.androidpim.fragments.*
import com.example.androidpim.service.BASE_URL
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.security.AccessController.getContext


class LkolPro : AppCompatActivity() {
    lateinit var mSharedPref: SharedPreferences
    lateinit var frag:Fragment
    lateinit var lostfound:Fragment
    lateinit var loggedAs:String
    lateinit var send_img:ImageView
    lateinit var map:ImageView
lateinit var bottomNavigation: BottomNavigationView
lateinit var toolbar: Toolbar;
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.profile -> {
                if(loggedAs == "user") {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, ProfileUser()).commit()
                }
                if(loggedAs == "club") {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, ProfileClub()).commit()
                }
                true
            }
            R.id.logout -> {
                mSharedPref.edit().clear().apply()
                val intent = Intent(applicationContext, LoginPro::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                true
            }
            R.id.messages -> {
                supportFragmentManager.beginTransaction().remove(frag).commit()
                supportFragmentManager.beginTransaction().remove(lostfound).commit()
                supportFragmentManager.beginTransaction().replace(R.id.frame, ListOfChat()).commit()
                supportFragmentManager.beginTransaction().remove(frag).commit()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Hide the status bar.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        setContentView(R.layout.activity_lkol_pro)
        toolbar=findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar);
        supportActionBar?.setDisplayShowTitleEnabled(false);

        frag = EventFragment()
        lostfound =lostfoundfrag()
         send_img = findViewById<ImageButton>(R.id.send_img)
        map = findViewById<ImageButton>(R.id.map)
        bottomNavigation=findViewById(R.id.bottom_navigation)
        val camera_img = findViewById<ImageView>(R.id.camera_img)
    map.setOnClickListener{

}
        send_img.setOnClickListener {
            if(loggedAs == "user") {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frame, ProfileUser()).commit()
            }
            if(loggedAs == "club") {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frame, ProfileClub()).commit()
            }

        }
        mSharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE)


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



        supportFragmentManager.beginTransaction().replace(R.id.frame, HomePro()).commit()
        supportFragmentManager.beginTransaction().replace(R.id.frameEvent, frag).commit()

        camera_img.setOnClickListener{
            mSharedPref.edit().clear().apply()
            val intent = Intent(applicationContext, LoginPro::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.page_1 -> {
                    println("u re in page 1")
                    // Respond to navigation item 1 click
                    supportFragmentManager.beginTransaction().replace(R.id.frame, HomePro()).commit()
                    supportFragmentManager.beginTransaction().replace(R.id.frameEvent, frag).commit()
                    supportFragmentManager.beginTransaction().remove(lostfound).commit()
                    return@OnNavigationItemSelectedListener true

                }
                R.id.page_2 -> {
                    println("u re in page 1")

                    supportFragmentManager.beginTransaction().replace(R.id.frame, ListOfCourses()).commit()
                    supportFragmentManager.beginTransaction().remove(frag).commit()
                    supportFragmentManager.beginTransaction().remove(lostfound).commit()
                    // Respond to navigation item 2 click
                 return@OnNavigationItemSelectedListener true
                }
                R.id.page_3 -> {
                    println("u re in page 1")

                    val intent = Intent(applicationContext, Scc::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    // Respond to navigation item 2 click
                    return@OnNavigationItemSelectedListener true

                }
                R.id.page_4 -> {
                    lostfound = lostfoundfrag()
                    supportFragmentManager.beginTransaction().replace(R.id.frame, lostfound).commit()
                    supportFragmentManager.beginTransaction().remove(frag).commit()
                    // Respond to navigation item 2 click
                    return@OnNavigationItemSelectedListener true

                }
                R.id.page_5 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frame, MapFrag()).commit()
                    supportFragmentManager.beginTransaction().remove(frag).commit()
                    // Respond to navigation item 2 click
                    return@OnNavigationItemSelectedListener true

                }
                else -> false

            }

        }
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        Glide.with(this) .asBitmap()
            .load(Uri.parse(ppp)).into(object : SimpleTarget<Bitmap?>(100,100) {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap?>?
                ) {
                    val drawable: Drawable = BitmapDrawable(resources, resource)
                    toolbar.collapseIcon = drawable
                    send_img.setImageDrawable(drawable)
                }
            })
    }






}