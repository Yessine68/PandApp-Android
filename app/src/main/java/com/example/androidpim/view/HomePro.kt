package com.example.androidpim.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidpim.R
import com.example.androidpim.models.Club
import com.example.androidpim.models.User
import com.example.androidpim.service.RetrofitApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomePro : Fragment(R.layout.fragment_home_pro) {




    override fun onCreateView(
        inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment


        val view = inflater.inflate(R.layout.fragment_home_pro, parent, false)

        val activity = activity as Context

        val instaStausList = view.findViewById<RecyclerView>(R.id.insta_status_list)
        instaStausList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        val apiuser = RetrofitApi.create().GetAllClubs()

        val statusList = ArrayList<Club>()
        apiuser.enqueue( object : Callback<List<Club>>{
            override fun onResponse(call: Call<List<Club>>?, response: Response<List<Club>>?) {

                if(response?.body() != null)
                    println(response.body()!!.size)
                if (response != null) {
                    for (i in 0 until response.body()!!.size)
                        statusList.add(response.body()!![i])
                }
                    //StatusAdapter.setMovieListItems(response.body()!!)
                val statusAdapter = StatusAdapter(activity,statusList)
                instaStausList.adapter = statusAdapter

            }

            override fun onFailure(call: Call<List<Club>>?, t: Throwable?) {

            }

        })
       /*


        for (i in 0 until status.size)
            statusList.add(InstaStatus(status[i].id, status[i].name, status[i].picture))
             */



        return view
    }


}