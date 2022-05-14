package com.example.androidpim.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.androidpim.ChatRoomActivity
import com.example.androidpim.R
import com.example.androidpim.models.chatList
import com.example.androidpim.pdfReader
import kotlinx.android.synthetic.main.activity_entrance.*
import org.jetbrains.anko.startActivity

class chatAdapter(val chatLists: MutableList<com.example.androidpim.models.chatList>) :  RecyclerView.Adapter<chatAdapter.chatList>(){


    var mContext: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): chatList {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.messageslist, parent, false)
        this.mContext = parent.getContext();

        return chatList(view)

    }

    class chatList (itemView: View) : RecyclerView.ViewHolder(itemView){
        val chatRoom = itemView.findViewById<TextView>(R.id.text)
        val userName = itemView.findViewById<TextView>(R.id.text2)
        val btn_show = itemView.findViewById<Button>(R.id.btnShow)

    }



    override fun onBindViewHolder(holder: chatList, position: Int) {
        val chatRoom = chatLists[position].chatRoom
        val userName = chatLists[position].userName
        holder.userName.text = userName
        holder.btn_show.setOnClickListener {
                    mContext?.startActivity<ChatRoomActivity>(
                        "userName" to userName,
                        "roomName" to chatRoom
                    )



        }

    }

    override fun getItemCount(): Int {
        return chatLists.size
    }
}