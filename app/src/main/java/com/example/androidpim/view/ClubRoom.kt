package com.example.androidpim.view

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidpim.R
import com.example.androidpim.adapters.ClubChatAdapter
import com.example.androidpim.adapters.MembersAdapter
import com.example.androidpim.models.ClubChat
import com.example.androidpim.models.Messageclub
import com.example.androidpim.service.BASE_URL
import com.example.androidpim.service.RetrofitApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ClubRoom : AppCompatActivity() {

    lateinit var mSharedPref: SharedPreferences
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
        mSharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE)
        val currentEmail = mSharedPref.getString("email", "user").toString()

        backButton = findViewById(R.id.backButton)
        chatRoompic = findViewById(R.id.chatRoompic)
        clubChatName = findViewById(R.id.clubChatName)
        framechat = findViewById(R.id.framechat)
        addMessageClub = findViewById(R.id.addMessageClub)
        sendMessageClub = findViewById(R.id.sendMessageClub)
        framechat.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        clubChatName.setText(intent.getStringExtra("esmelclub"))



        var messageClubList = ArrayList<Messageclub>()
        var fakeMsg = Messageclub()
        fakeMsg.textMessage = "aaaaaa"
        fakeMsg.userId = currentEmail
        fakeMsg.clubChat = intent.getStringExtra("clubchatId")
        fakeMsg.time ="sam 17 avril 17h:12"

        val apiFetchMessages = RetrofitApi.create().getChatRoomByClub(intent.getStringExtra("clubchatName"))
        apiFetchMessages.enqueue(object : Callback<ClubChat>{
            override fun onResponse(call: Call<ClubChat>, response: Response<ClubChat>) {
                if(response.isSuccessful){
                    for (i in 0 until (response.body()!!.messageclubs?.size ?: 10)){
                        val ppp = BASE_URL +"upload/download/"+intent.getStringExtra("clubLogo")
                        Glide.with(applicationContext)
                            .load(ppp)
                            .into(chatRoompic)
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

        backButton.setOnClickListener{
            finish()
        }
        sendMessageClub.setOnClickListener{
            if(addMessageClub.text.toString().isEmpty()){
                Toast.makeText(applicationContext, "cannot send empty message", Toast.LENGTH_SHORT).show()
            }
            else{
                var sendMess = Messageclub()
                sendMess.clubChat = intent.getStringExtra("clubchatId")
                sendMess.userId = currentEmail
                sendMess.textMessage = addMessageClub.text.toString()
                sendMess.userImage = mSharedPref.getString("profilePicture","zwayten").toString()

                val apiSendMessageClub = RetrofitApi.create().sendMessageClub(sendMess)
                apiSendMessageClub.enqueue(object : Callback<Messageclub>{
                    override fun onResponse(
                        call: Call<Messageclub>,
                        response: Response<Messageclub>
                    ) {

                        val apiFetchMessagess = RetrofitApi.create().getChatRoomByClub(intent.getStringExtra("clubchatName"))
                        messageClubList.clear()
                        apiFetchMessagess.enqueue(object : Callback<ClubChat>{
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

                    override fun onFailure(call: Call<Messageclub>, t: Throwable) {
                        println("mochkla Ya dah")
                    }

                })
            }



        }




        //---------




    }
}