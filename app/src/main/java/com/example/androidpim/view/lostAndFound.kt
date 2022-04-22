package com.example.androidpim.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.viewpager.widget.ViewPager
import com.example.androidpim.R
import com.example.androidpim.adapters.ViewPagerAdapter
import com.example.androidpim.fragments.fragmentLost
import com.example.androidpim.fragments.fragmentfound
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout

class lostAndFound : AppCompatActivity() {
    private val context: Context = this

    private val lostFrag: fragmentLost = fragmentLost()
    private val foundFrag: fragmentfound = fragmentfound()
    val POST_ROUTE = "com.example.lostfound.postpage"

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(androidx.appcompat.R.anim.abc_fade_in, androidx.appcompat.R.anim.abc_fade_out)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_view)

        // Initialize
        var viewPager = findViewById<View>(R.id.viewPager) as ViewPager
        var tabLayout = findViewById<View>(R.id.tabLayout) as TabLayout
        var searchView = findViewById<View>(R.id.searchView) as SearchView
        var buttonCreate = findViewById<View>(R.id.buttonCreate) as FloatingActionButton

        supportActionBar?.hide();



        // Listen to search and populate the searched item
          searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
             override fun onQueryTextSubmit(query: String): Boolean {
                 return false
             }

          override fun onQueryTextChange(newText: String): Boolean {
               if (viewPager.currentItem == 0) {
                     lostFrag.refreshList(newText)
                 } else if (viewPager.currentItem == 1) {
                     foundFrag.refreshList(newText)
                 }
                 return false
             }
        })
        // Set drawer
        buttonCreate.setOnClickListener {
            // Create post
            val intent = Intent(this, upload_post::class.java)
            if (viewPager.currentItem == 0) {
                intent.putExtra("data", "lost")
            } else if (viewPager.currentItem == 1) {
                intent.putExtra("data", "found")
            }
            startActivity(intent)
        }



        val adapter = ViewPagerAdapter(supportFragmentManager)

        // Add fragments
        adapter.addFragment(lostFrag, "LOST")
        adapter.addFragment(foundFrag, "Found")

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)


    }

}