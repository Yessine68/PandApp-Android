package com.example.androidpim.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidpim.R
import com.example.androidpim.adapters.EventAdapter
import com.example.androidpim.models.Event
import com.example.androidpim.models.User
import com.example.androidpim.service.RetrofitApi
import com.example.androidpim.view.StatusAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventFragment: Fragment(R.layout.fragment_event) {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_event, container, false)
        val activity = activity as Context

        val eventRecycler = rootView.findViewById<RecyclerView>(R.id.eventlist)
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






        return rootView
    }

}