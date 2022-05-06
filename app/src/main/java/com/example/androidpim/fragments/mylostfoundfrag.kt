package com.example.androidpim.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.viewpager.widget.ViewPager
import com.example.androidpim.R
import com.example.androidpim.adapters.ViewPagerAdapter
import com.example.androidpim.view.upload_post
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.marcoscg.dialogsheet.DialogSheet

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [lostfoundfrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class mylostfoundfrag : Fragment() {
    var mContext: Context? = null
   lateinit var viewPager:ViewPager
     val lostFrag: myfragmentLost = myfragmentLost()
     val foundFrag: myfragmentfound = myfragmentfound()
    val POST_ROUTE = "com.example.lostfound.postpage"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var tabLayout = view.findViewById<View>(R.id.tabLayout) as TabLayout

        super.onViewCreated(view, savedInstanceState)
         viewPager = view.findViewById<View>(R.id.viewPager) as ViewPager

        var adapter = ViewPagerAdapter(childFragmentManager)
        // Add fragments
        adapter.addFragment(lostFrag, "LOST")
        adapter.addFragment(foundFrag, "Found")
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_lostfoundfrag, container, false)
        this.mContext = view.context;

         viewPager = view.findViewById<View>(R.id.viewPager) as ViewPager

        // Inflate the layout for this fragment
        var searchView = view.findViewById<View>(R.id.searchView) as SearchView
        var buttonCreate = view.findViewById<View>(R.id.buttonCreate) as FloatingActionButton
      /*  val lostDilog = this.mContext?.let { it1 -> DialogSheet(it1, true) }
        lostDilog?.setView(R.layout.activity_upload_post)
        val inflatedView2 = lostDilog?.inflatedView
        lostDilog?.show();*/


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

        buttonCreate.setOnClickListener {
            // Create post
           val intent = Intent(context, upload_post::class.java)
            if (viewPager.currentItem == 0) {
                intent.putExtra("data", "lost")
            } else if (viewPager.currentItem == 1) {
                intent.putExtra("data", "found")
            }
            startActivity(intent)
        }
        


      return  view
    }




}