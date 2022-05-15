package com.example.androidpim

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.androidpim.adapters.PostAdapter
import com.example.androidpim.models.Post
import com.example.androidpim.service.BASE_URL
import com.example.androidpim.service.ElearningApi
import com.google.gson.Gson
import com.junga.socketio_android.model.MessageType
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_chatroom.*
import kotlinx.android.synthetic.main.fragment_home_pro.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class ChatRoomActivity : AppCompatActivity(), View.OnClickListener {


    val TAG = ChatRoomActivity::class.java.simpleName
    lateinit var mSharedPref: SharedPreferences


    lateinit var mSocket: Socket;
    lateinit var userName: String;
    lateinit var roomName: String;
    lateinit var userPost: String;
    lateinit var layoutManager: LinearLayoutManager;
    lateinit var username:TextView;
    lateinit var picPartner:ImageView;



    val gson: Gson = Gson()
    //For setting the recyclerView.
    val chatList: ArrayList<Message> = arrayListOf();
    lateinit var chatRoomAdapter: ChatRoomAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatroom)
        send.setOnClickListener(this)
        leave.setOnClickListener(this)
        //Get the nickname and roomname from entrance activity.

            userName = intent.getStringExtra("userName")!!
            roomName = intent.getStringExtra("roomName")!!
            userPost = intent.getStringExtra("userPost")!!
            picPartner=findViewById<ImageView>(R.id.partnerpic)
            username = findViewById<TextView>(R.id.partnerName)
            username.setText(userPost)

            val ppp = BASE_URL+"upload/download/user/"+userPost
            Glide.with(this).load(Uri.parse(ppp)).into(picPartner)
            mSharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE)
            val name=mSharedPref.getString("FirstName", "zwayten").toString()

            println("hollla mother  ")

        try {
            val apiInterface = ElearningApi.create()
            apiInterface.GetAllMessages(roomName).enqueue(object: Callback<List<Message>> {
                override fun onResponse(call: Call<List<Message>>, response: Response<List<Message>>) {
                    if(response.isSuccessful){
                        println("hollla mother  "+ response.body().toString())
for(msage in response.body()!!){
    if (msage.userName==name){
        msage.viewType = MessageType.CHAT_MINE.index
        addItemToRecyclerView(msage)
    }else {
        msage.viewType = MessageType.CHAT_PARTNER.index
        addItemToRecyclerView(msage)

    }


}


                        Log.i("yessss", response.body().toString())
                    } else {
                        Log.i("nooooo", response.body().toString())

                    }
                }
                override fun onFailure(call: Call<List<Message>>, t: Throwable) {
                    t.printStackTrace()
                    println("OnFailure")
                }

            })
            println(userName)
            println("chat rooo;;"+roomName)




        } catch (e: Exception) {
            e.printStackTrace()
        }
        //Set Chatroom adapter
        chatRoomAdapter = ChatRoomAdapter(this, chatList);
        recyclerView.adapter = chatRoomAdapter;
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        //Let's connect to our Chat room! :D
        try {
            mSocket = IO.socket(BASE_URL)
            Log.d("success", mSocket.id())

        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("fail", "Failed to connect")
        }


        mSocket.connect()
        mSocket.on(Socket.EVENT_CONNECT, onConnect)
        mSocket.on("newUserToChatRoom", onNewUser)
        mSocket.on("updateChat", onUpdateChat)
        mSocket.on("userLeftChatRoom", onUserLeft)
    }

    var onUserLeft = Emitter.Listener {
        val leftUserName = it[0] as String
        val chat: Message = Message(leftUserName, "", "", MessageType.USER_LEAVE.index)
        addItemToRecyclerView(chat)
    }

    var onUpdateChat = Emitter.Listener {
        val chat: Message = gson.fromJson(it[0].toString(), Message::class.java)
        chat.viewType = MessageType.CHAT_PARTNER.index
        addItemToRecyclerView(chat)
    }

    var onConnect = Emitter.Listener {
        val data = initialData(userName, roomName)
        val jsonData = gson.toJson(data)
        mSocket.emit("subscribe", jsonData)

    }

    var onNewUser = Emitter.Listener {
        val name = it[0] as String //This pass the userName!
        val chat = Message(name, "", roomName, MessageType.USER_JOIN.index)
        addItemToRecyclerView(chat)
        Log.d(TAG, "on New User triggered.")
    }


    private fun sendMessage() {
        val content = editText.text.toString()
        val sendData = SendMessage(userName, content, roomName)
        val jsonData = gson.toJson(sendData)
        mSocket.emit("newMessage", jsonData)

        val message = Message(userName, content, roomName, MessageType.CHAT_MINE.index)
        val apiInterface = ElearningApi.create()
        apiInterface.addMessage(message).enqueue(object: Callback<Message> {
            override fun onResponse(
                call: Call<Message>,
                response: Response<Message>
            ) {
                if(response.isSuccessful){
                    Log.i("onResponse goooood", response.body().toString())

                } else {
                    Log.i("OnResponse not good", response.body().toString())
                }
            }

            override fun onFailure(call: Call<Message>, t: Throwable) {

                println("noooooooooooooooooo")
            }

        })




        addItemToRecyclerView(message)
    }

    private fun addItemToRecyclerView(message: Message) {

        //Since this function is inside of the listener,
        // You need to do it on UIThread!
        runOnUiThread {
            chatList.add(message)
            chatRoomAdapter.notifyItemInserted(chatList.size)
            editText.setText("")
            recyclerView.scrollToPosition(chatList.size - 1) //move focus on last message
        }
    }
    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.send -> sendMessage()
            R.id.leave -> deestroy()
        }
    }
      fun deestroy() {
         val data = initialData(userName, roomName)
        val jsonData = gson.toJson(data)
        mSocket.emit("unsubscribe", jsonData)
        mSocket.disconnect()
finish()
    }

}
