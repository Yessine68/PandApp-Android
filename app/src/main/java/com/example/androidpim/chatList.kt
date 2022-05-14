package com.example.androidpim

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidpim.fragments.ListOfCourses

class chatList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_courses)
        supportFragmentManager.beginTransaction().replace(R.id.flfragment, ListOfCourses()).commit()

    }
}