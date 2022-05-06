package com.example.androidpim.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.androidpim.EntranceActivity
import com.example.androidpim.R
import com.example.androidpim.adapters.myPostAdapter
import com.example.androidpim.models.Post
import com.example.androidpim.service.LostPostApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
/**
 * A simple [Fragment] subclass.
 * Use the [myfragmentLost.newInstance] factory method to
 * create an instance of this fragment.
 */
class myfragmentLost : Fragment() {
    // TODO: Rename and change types of parameters


    lateinit var listView:  ListView;
    private var postList: ArrayList<Post>? = ArrayList<Post>()
    lateinit var mSharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mSharedPref =  requireActivity().getSharedPreferences("UserPref", Context.MODE_PRIVATE)
       val view = inflater.inflate(R.layout.fragment_lost, container, false)
        val id: String = mSharedPref.getString("email", "zwayten").toString()

        listView = view!!.findViewById<ListView>(R.id.listView) as ListView
        // postList =  ArrayList<Post>()
        val apiInterface = LostPostApi.create()
        apiInterface.GetMyAllLost(id).enqueue(object: Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {

                if(response.isSuccessful){
                    for(post in response.body()!!)
                    {
                        Log.i("ghassen", post.toString())
                        postList?.add(post)

                    }

                    Collections.reverse(response.body()!!)
                    val parentActivity = view.context as FragmentActivity
                    val postAdapter = myPostAdapter(parentActivity,response.body()!!)
                    listView.adapter = postAdapter

                    Log.i("yessss", response.body().toString())
                } else {
                    Log.i("nooooo", response.body().toString())

                }
            }
            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                t.printStackTrace()
                println("OnFailure")
            }

        })


        listView.setOnItemClickListener { parent, view, position, id ->
println("el position ya gaston "+postList?.get(position))
            val intent = Intent(context, EntranceActivity::class.java)
            intent.putExtra("username","")
            startActivity(intent)

        }

        return view

    }

    fun refreshList(search: String) {
        val parentActivity = requireView().context as FragmentActivity
        val mFinalList: MutableList<Post> = java.util.ArrayList()
        for (p in postList!!) {
            if (p.objet.toLowerCase().contains(search.toLowerCase())) {
                mFinalList.add(p)
            }
        }
        val postAdapter = myPostAdapter(parentActivity, mFinalList)
        //PostAdapter postAdapter = new PostAdapter(parentActivity,postList);
        //postAdapter.getFilter().filter(search);
        listView.adapter = postAdapter
    }




}

