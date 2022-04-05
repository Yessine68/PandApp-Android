package com.example.androidpim.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.androidpim.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        //hide action bar
        supportActionBar?.hide();
        lateinit var handler: Handler

        handler= Handler()
        handler.postDelayed({
            val intent= Intent(this,Login::class.java)
            startActivity(intent)
            finish()
        },3000)

    }
}