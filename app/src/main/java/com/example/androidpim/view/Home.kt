package com.example.androidpim.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.androidpim.R

class Home : AppCompatActivity() {
    lateinit var     postfile: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    postfile=findViewById(R.id.post_file)

    postfile.setOnClickListener {

        val intent = Intent(applicationContext, upload_post::class.java)
        startActivity(intent)
    }
    }

}