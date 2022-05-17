package com.example.androidpim.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidpim.R
import com.example.androidpim.adapters.EventAdapter
import com.example.androidpim.fragments.DocumentsFragment
import com.example.androidpim.models.Event
import com.example.androidpim.service.BASE_URL
import com.example.androidpim.service.RetrofitApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileClub: Fragment(R.layout.club_profile) {
    lateinit var mSharedPref: SharedPreferences



    lateinit var imageProfileclub: ImageView
    lateinit var usernameProfileclub: TextView
    lateinit var emailprofileclub: TextView
    ////lateinit var editprofilebutton: Button
    lateinit var membersbutton: Button
    lateinit var addEvent:Button

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.club_profile, parent, false)

        mSharedPref = requireActivity().getSharedPreferences("UserPref", Context.MODE_PRIVATE)

        imageProfileclub = view.findViewById(R.id.imageProfileclub)
        usernameProfileclub = view.findViewById(R.id.usernameProfileclub)
        membersbutton = view.findViewById(R.id.membersbutton)

        addEvent = view.findViewById(R.id.addEvent)


        val activity = activity as Context

        val eventRecycler = view.findViewById<RecyclerView>(R.id.framedll)
        eventRecycler.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        val apiuser = RetrofitApi.create().GetEvents()

        var eventList = ArrayList<Event>()
        apiuser.enqueue( object : Callback<List<Event>> {
            override fun onResponse(call: Call<List<Event>>?, response: Response<List<Event>>?) {

                if(response?.body() != null)
                //println(response.body()!!)
                    if (response != null) {
                        for (i in 0 until response.body()!!.size)

                            eventList.add(response.body()!![i])
                        println("ttttttttttttttttttt"+eventList.toString())

                    }
                //StatusAdapter.setMovieListItems(response.body()!!)
                val eventAdapter = EventAdapter(eventList)
                eventRecycler.adapter = eventAdapter

            }

            override fun onFailure(call: Call<List<Event>>?, t: Throwable?) {

            }

        })

        membersbutton.setOnClickListener {
            val intent = Intent(view.context, Members::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }


        //editprofilebutton = view.findViewById(R.id.editprofilebutton)
       // editprofilebutton = view.findViewById(R.id.editprofilebutton)
     //  documentsrequest = view.findViewById(R.id.documentsrequest)



        val email: String = mSharedPref.getString("login", "zwayten").toString()
        val clubName: String = mSharedPref.getString("ClubName", "zwayten").toString()

        //val phone: String = mSharedPref.getString("clubLogo", "zwayten").toString()


        usernameProfileclub.text = clubName;
        //emailprofileclub.text = email;



        val picStr: String = mSharedPref.getString("clubLogo", "email").toString()
        println("###############################################"+picStr)
        val ppp = BASE_URL +"upload/download/"+picStr
        Glide.with(this).load(Uri.parse(ppp)).into(imageProfileclub)


        addEvent.setOnClickListener {
            val intent = Intent(view.context, AddPost::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)

        }




    //    editprofilebutton.setOnClickListener {
    //        var lkolPro = (activity as LkolPro)
    //        lkolPro.supportFragmentManager.beginTransaction().replace(R.id.frame, UserEdit()).commit()
    //        println("édfsgfjhsdfvsjfksfvvfghqfvsfqlfglqfhsgfsbfjsfgsjfhjdslkfgsflsfjhsklfgsks544sd52f452s4fs2f452s")
    //    }

    //    documentsrequest.setOnClickListener {
    //        var lkolPro = (activity as LkolPro)
    //        lkolPro.supportFragmentManager.beginTransaction().replace(R.id.frame, DocumentsFragment()).commit()
    //    }

        return view
    }
}