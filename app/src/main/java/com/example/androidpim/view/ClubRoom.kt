package com.example.androidpim.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidpim.R
import com.example.androidpim.adapters.ClubChatAdapter
import com.example.androidpim.adapters.MembersAdapter
import com.example.androidpim.models.ClubChat
import com.example.androidpim.models.Messageclub
import com.example.androidpim.service.RetrofitApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ClubRoom : AppCompatActivity() {
    lateinit var backButton: ImageView
    lateinit var chatRoompic: ImageView
    lateinit var clubChatName: TextView
    lateinit var framechat: RecyclerView
    lateinit var addMessageClub: EditText
    lateinit var sendMessageClub: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_club_room)

        //--------

        backButton = findViewById(R.id.backButton)
        chatRoompic = findViewById(R.id.chatRoompic)
        clubChatName = findViewById(R.id.clubChatName)
        framechat = findViewById(R.id.framechat)
        addMessageClub = findViewById(R.id.addMessageClub)
        sendMessageClub = findViewById(R.id.sendMessageClub)

        framechat.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        //---------

        var messageClubList = ArrayList<Messageclub>()
        var fakeMsg = Messageclub()
        fakeMsg.textMessage = "aaaaaa"
        fakeMsg.userId = "yassine.zitoun@esprit.tn"
        fakeMsg.clubChat = "6283fb3470441e5bb5123cdd"
        fakeMsg.time ="sam 17 avril 17h:12"

        val apiFetchMessages = RetrofitApi.create().getChatRoomByClub("zwayten111@gmail.com")
        apiFetchMessages.enqueue(object : Callback<ClubChat>{
            override fun onResponse(call: Call<ClubChat>, response: Response<ClubChat>) {
                if(response.isSuccessful){
                    for (i in 0 until (response.body()!!.messageclubs?.size ?: 10)){

                        messageClubList.add(response.body()!!.messageclubs?.get(i) ?: fakeMsg)


                    }
                }
                val clubChatAdapter = ClubChatAdapter(applicationContext,messageClubList)
                framechat.adapter = clubChatAdapter
            }

            override fun onFailure(call: Call<ClubChat>, t: Throwable) {
                println("mochkla Ya dah")
            }

        })


    }
}