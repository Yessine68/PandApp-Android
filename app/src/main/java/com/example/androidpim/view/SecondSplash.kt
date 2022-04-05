package com.example.androidpim.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.androidpim.R

class SecondSplash : AppCompatActivity() {
    lateinit var signin: Button
    lateinit var signup: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_splash)
        signin = findViewById(R.id.signin)
        signup = findViewById(R.id.signup)
        signin.setOnClickListener { val intent= Intent(this,Login::class.java)
            startActivity(intent)
            finish() }

        signup.setOnClickListener {
             val intent= Intent(this,Signup::class.java)
                startActivity(intent)
                finish()
        }

    }
}