package com.example.androidpim.adapters

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.androidpim.ChatRoomActivity
import com.example.androidpim.R
import com.example.androidpim.models.chatList
import com.example.androidpim.pdfReader
import kotlinx.android.synthetic.main.activity_entrance.*
import org.jetbrains.anko.startActivity

class chatAdapter( private val context: FragmentActivity, val chatLists: MutableList<com.example.androidpim.models.chatList>) :  RecyclerView.Adapter<chatAdapter.chatList>(){
    lateinit var mSharedPref: SharedPreferences

lateinit var email:String;
    lateinit var name:String;

    var mContext: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): chatList {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.messageslist, parent, false)
        this.mContext = parent.getContext();
        mSharedPref = context.getSharedPreferences("UserPref", Context.MODE_PRIVATE)
        this.email=mSharedPref.getString("email", "zwayten").toString()
        this.name=mSharedPref.getString("email", "zwayten").toString()

        return chatList(view)

    }

    class chatList (itemView: View) : RecyclerView.ViewHolder(itemView){
        val userName = itemView.findViewById<TextView>(R.id.text2)
        val btn_show = itemView.findViewById<Button>(R.id.btnShow)

    }



    override fun onBindViewHolder(holder: chatList, position: Int) {
        var userName:String
        val chatRoom = chatLists[position].chatRoom
        val reciver= chatLists[position].userNameReciver
val userPost =chatLists[position].userName

        userName = if(email == chatLists[position].emailUser){
            chatLists[position].userName
        }else{
            chatLists[position].userNameReciver
        }
        holder.userName.text = userName
        holder.btn_show.setOnClickListener {
                    mContext?.startActivity<ChatRoomActivity>(
                        "userName" to userPost,
                        "roomName" to chatRoom,
                        "userPost" to userName
                        )



        }

    }

    override fun getItemCount(): Int {
        return chatLists.size
    }
}