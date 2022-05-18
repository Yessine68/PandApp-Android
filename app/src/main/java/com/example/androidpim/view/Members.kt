package com.example.androidpim.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.androidpim.R
import com.example.androidpim.adapters.MembersAdapter
import com.example.androidpim.models.ClubMembers
import com.example.androidpim.service.RetrofitApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import swipe.gestures.GestureManager
import swipe.gestures.GestureManager.SwipeCallbackLeft
import swipe.gestures.GestureManager.SwipeCallbackRight

class Members : AppCompatActivity() {
    lateinit var mSharedPref: SharedPreferences
    lateinit var frameMembers: RecyclerView
    lateinit var frameMembersIn: RecyclerView
    lateinit var imageViewBackadd: ImageView
    //lateinit var swipeRefreshLayout: SwipeRefreshLayout

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        mSharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE)
        val currentLogin = mSharedPref.getString("login","zwayten").toString()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_members)

        val membersRequest = ArrayList<ClubMembers>()
        val membersIn = ArrayList<ClubMembers>()

        frameMembers = findViewById(R.id.frameMembers)
        frameMembers.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        frameMembersIn = findViewById(R.id.frameMembersIn)
        frameMembersIn.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //swipeRefreshLayout = findViewById(R.id.swipe)



        imageViewBackadd = findViewById(R.id.imageViewBackadd)
        imageViewBackadd.setOnClickListener{
           finish()
        }

        val apiuser = RetrofitApi.create().getClubMembers(currentLogin)
        apiuser.enqueue( object : Callback<List<ClubMembers>> {
            override fun onResponse(
                call: Call<List<ClubMembers>>,
                response: Response<List<ClubMembers>>
            ) {
                if (response != null) {
                    for (i in 0 until response.body()!!.size){
                        if(response.body()!![i].state == false){
                            membersRequest.add(response.body()!![i])
                        }
                        if(response.body()!![i].state == true){
                            membersIn.add(response.body()!![i])
                        }

                    }


                }
                //StatusAdapter.setMovieListItems(response.body()!!)
                val clubMemebrsAdapter = MembersAdapter(membersRequest)
                frameMembers.adapter = clubMemebrsAdapter
                val clubMemebrsAdapterIn = MembersAdapter(membersIn)
                frameMembersIn.adapter = clubMemebrsAdapterIn
            }

            override fun onFailure(call: Call<List<ClubMembers>>, t: Throwable) {
                println("mochkol")
            }

        })
        val clubMemebrsAdapter = MembersAdapter(membersRequest)
        val rightCallback =
            SwipeCallbackRight { position ->

                clubMemebrsAdapter.notifyDataSetChanged();
                val apiDeleteMember = RetrofitApi.create().deleteMember(membersRequest.get(position)._id.toString())
                apiDeleteMember.enqueue( object : Callback<ClubMembers> {
                    override fun onResponse(
                        call: Call<ClubMembers>,
                        response: Response<ClubMembers>
                    ) {
                        val apiuser = RetrofitApi.create().getClubMembers(currentLogin)
                        apiuser.enqueue( object : Callback<List<ClubMembers>> {
                            override fun onResponse(
                                call: Call<List<ClubMembers>>,
                                response: Response<List<ClubMembers>>
                            ) {
                                membersRequest.clear()
                                membersIn.clear()
                                if (response != null) {
                                    for (i in 0 until response.body()!!.size){
                                        if(response.body()!![i].state == false){
                                            membersRequest.add(response.body()!![i])
                                        }
                                        if(response.body()!![i].state == true){
                                            membersIn.add(response.body()!![i])
                                        }

                                    }


                                }
                                //StatusAdapter.setMovieListItems(response.body()!!)
                                val clubMemebrsAdapter = MembersAdapter(membersRequest)
                                frameMembers.adapter = clubMemebrsAdapter
                                val clubMemebrsAdapterIn = MembersAdapter(membersIn)
                                frameMembersIn.adapter = clubMemebrsAdapterIn
                            }

                            override fun onFailure(call: Call<List<ClubMembers>>, t: Throwable) {
                                println("mochkol")
                            }

                        })
                    }

                    override fun onFailure(call: Call<ClubMembers>, t: Throwable) {
                        println("mochkol")
                    }

                })

            }


        val leftCallback =
            SwipeCallbackLeft { position ->

                clubMemebrsAdapter.notifyDataSetChanged();
                val apiJoinClub = RetrofitApi.create().joinclub(membersRequest.get(position)._id)
                apiJoinClub.enqueue( object : Callback<ClubMembers> {
                    override fun onResponse(
                        call: Call<ClubMembers>,
                        response: Response<ClubMembers>
                    ) {
                        val apiuser = RetrofitApi.create().getClubMembers(currentLogin)
                        apiuser.enqueue( object : Callback<List<ClubMembers>> {
                            override fun onResponse(
                                call: Call<List<ClubMembers>>,
                                response: Response<List<ClubMembers>>
                            ) {
                                membersRequest.clear()
                                membersIn.clear()
                                if (response != null) {
                                    for (i in 0 until response.body()!!.size){
                                        if(response.body()!![i].state == false){
                                            membersRequest.add(response.body()!![i])
                                        }
                                        if(response.body()!![i].state == true){
                                            membersIn.add(response.body()!![i])
                                        }

                                    }


                                }
                                //StatusAdapter.setMovieListItems(response.body()!!)
                                val clubMemebrsAdapter = MembersAdapter(membersRequest)
                                frameMembers.adapter = clubMemebrsAdapter
                                val clubMemebrsAdapterIn = MembersAdapter(membersIn)
                                frameMembersIn.adapter = clubMemebrsAdapterIn
                            }

                            override fun onFailure(call: Call<List<ClubMembers>>, t: Throwable) {
                                println("mochkol")
                            }

                        })
                    }

                    override fun onFailure(call: Call<ClubMembers>, t: Throwable) {
                        println("mochkol")
                    }

                })

            }


        val clubMemebrsAdapterIn = MembersAdapter(membersIn)
        val rightCallbackIn =
            SwipeCallbackRight { position ->

                clubMemebrsAdapter.notifyDataSetChanged();
                val apiDeleteMember = RetrofitApi.create().deleteMember(membersIn.get(position)._id.toString())
                apiDeleteMember.enqueue( object : Callback<ClubMembers> {
                    override fun onResponse(
                        call: Call<ClubMembers>,
                        response: Response<ClubMembers>
                    ) {
                        val apiuser = RetrofitApi.create().getClubMembers(currentLogin)
                        apiuser.enqueue( object : Callback<List<ClubMembers>> {
                            override fun onResponse(
                                call: Call<List<ClubMembers>>,
                                response: Response<List<ClubMembers>>
                            ) {
                                membersRequest.clear()
                                membersIn.clear()
                                if (response != null) {
                                    for (i in 0 until response.body()!!.size){
                                        if(response.body()!![i].state == false){
                                            membersRequest.add(response.body()!![i])
                                        }
                                        if(response.body()!![i].state == true){
                                            membersIn.add(response.body()!![i])
                                        }

                                    }


                                }
                                //StatusAdapter.setMovieListItems(response.body()!!)
                                val clubMemebrsAdapter = MembersAdapter(membersRequest)
                                frameMembers.adapter = clubMemebrsAdapter
                                val clubMemebrsAdapterIn = MembersAdapter(membersIn)
                                frameMembersIn.adapter = clubMemebrsAdapterIn
                            }

                            override fun onFailure(call: Call<List<ClubMembers>>, t: Throwable) {
                                println("mochkol")
                            }

                        })
                    }

                    override fun onFailure(call: Call<ClubMembers>, t: Throwable) {
                        println("mochkol")
                    }

                })

            }


        val recyclerAdapterSwipeGestures = GestureManager(rightCallback, leftCallback)
        val recyclerAdapterSwipeGesturesIn = GestureManager(rightCallbackIn)
        //Set Text
        recyclerAdapterSwipeGesturesIn.setTextRight("Kick")
        recyclerAdapterSwipeGesturesIn.setTextSize(30)
        recyclerAdapterSwipeGesturesIn.setTextColor(Color.WHITE)
        recyclerAdapterSwipeGesturesIn.setBackgroundColorLeft(ColorDrawable(Color.RED))
        val itemTouchHelperIn = ItemTouchHelper(recyclerAdapterSwipeGesturesIn)
        itemTouchHelperIn.attachToRecyclerView(frameMembersIn)



        //Set Text
        recyclerAdapterSwipeGestures.setTextLeft("Accept")
        recyclerAdapterSwipeGestures.setTextRight("Decline")

        //Set text size

        //Set text size
        recyclerAdapterSwipeGestures.setTextSize(30)

        //Set text colour

        //Set text colour
        recyclerAdapterSwipeGestures.setTextColor(Color.WHITE)

        // Setting background colours

        // Setting background colours
        recyclerAdapterSwipeGestures.setBackgroundColorLeft(ColorDrawable(Color.RED))
        recyclerAdapterSwipeGestures.setBackgroundColorRight(ColorDrawable(Color.GREEN))
        // Attach to the Recycler View Adapter
        val itemTouchHelper = ItemTouchHelper(recyclerAdapterSwipeGestures)
        itemTouchHelper.attachToRecyclerView(frameMembers)
        clubMemebrsAdapter.notifyDataSetChanged();



    }
}


