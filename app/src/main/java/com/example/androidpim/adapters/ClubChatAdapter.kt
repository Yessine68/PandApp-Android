package com.example.androidpim.adapters

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidpim.R
import com.example.androidpim.models.Messageclub
import com.example.androidpim.service.BASE_URL
import kotlinx.coroutines.withContext


class ClubChatAdapter(private val context: Context, val messageList: List<Messageclub>) : RecyclerView.Adapter<ClubChatAdapter.ClubChatViewHolder>() {
    lateinit var mSharedPref: SharedPreferences

    var mContext: Context? = null
     var test: Int = 3




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubChatViewHolder {

println("teammm zwayten")
        val inflater = LayoutInflater.from(parent.context)
        this.mContext = parent.getContext()

        mSharedPref = parent.context.getSharedPreferences("UserPref", Context.MODE_PRIVATE)
        val currentEmail = mSharedPref.getString("email", "user").toString()

        println("zwaytennnnnnnnnnnnnnnnnnnnnnnnnnnnn:   "+currentEmail)
        print(messageList.size)
        if(viewType == 0){
           val view = inflater.inflate(R.layout.messageviewsender, parent, false)
            return ClubChatViewHolder(view)
        }else{
            val view = inflater.inflate(R.layout.messageview, parent, false)
            return ClubChatViewHolder(view)
        }


    }

    override fun getItemViewType(position: Int): Int {

        mSharedPref = context.getSharedPreferences("UserPref", Context.MODE_PRIVATE)
        val currentEmail = mSharedPref.getString("email", "user").toString()
        return if (messageList[position].userId == currentEmail) 0 else 1
    }

    override fun onBindViewHolder(holder: ClubChatViewHolder, position: Int) {

        val ppp = BASE_URL +"upload/download/"+messageList[position].userImage//membersList[position].memberPicture
        Glide.with(mContext!!)
            .load(ppp)
            .into(holder.chatOtherProfilePic)

        holder.otherText.setText(messageList[position].textMessage)
        holder.messageTime.setText(messageList[position].time)

    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    class ClubChatViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        var chatOtherProfilePic = itemView.findViewById<ImageView>(R.id.chatOtherProfilePic)
        var otherText = itemView.findViewById<TextView>(R.id.otherText)
        var messageTime = itemView.findViewById<TextView>(R.id.messageTime)

    }
}