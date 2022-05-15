package com.example.androidpim.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidpim.R
import com.example.androidpim.adapters.chatAdapter
import com.example.androidpim.models.chatList
import com.example.androidpim.service.ElearningApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ListOfChat : Fragment() {

    lateinit var recylcerChampionAdapter: chatAdapter
    lateinit var carRecyclerView: RecyclerView
    lateinit var mSharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView= inflater.inflate(R.layout.fragment_list_of_courses, container, false)
        var eleringList : MutableList<chatList> = ArrayList()
        mSharedPref = requireActivity().getSharedPreferences("UserPref", Context.MODE_PRIVATE)
        val email: String = mSharedPref.getString("email", "zwayten").toString()

        carRecyclerView = rootView.findViewById(R.id.recycle_learning)
        val apiInterface = ElearningApi.create()
apiInterface.GetAllChat(email).enqueue(object: Callback<List<chatList>> {
    override fun onResponse(call: Call<List<chatList>>, response: Response<List<chatList>>) {
        if(response.isSuccessful){
            eleringList= response.body() as MutableList<chatList>;
            recylcerChampionAdapter = chatAdapter(context as FragmentActivity,eleringList)
            carRecyclerView.adapter = recylcerChampionAdapter
            carRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,
                false)
            Log.i("yessss", response.body().toString())
            //}
        } else {
            Log.i("nooooo", response.body().toString())                }
    }

    override fun onFailure(call: Call<List<chatList>>, t: Throwable) {
        println("OnFailure error ")

        t.printStackTrace()
    }

})





        return rootView

    }

}