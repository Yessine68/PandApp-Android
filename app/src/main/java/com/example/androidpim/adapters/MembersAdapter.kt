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
import com.example.androidpim.models.ClubMembers
import com.example.androidpim.service.BASE_URL

class MembersAdapter(val membersList: List<ClubMembers>) : RecyclerView.Adapter<MembersAdapter.MembersViewHolder>() {
    lateinit var mSharedPref: SharedPreferences

    var mContext: Context? = null





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MembersViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.member_single, parent, false)
        this.mContext = parent.getContext()
        return MembersViewHolder(view)
    }

    override fun onBindViewHolder(holder: MembersViewHolder, position: Int) {
        val ppp = BASE_URL +"upload/download/"+membersList[position].memberPicture
        Glide.with(mContext!!)
            .load(ppp)
            .into(holder.avatar_iv)

        holder.name_tv.setText(membersList[position].userName)
        holder.email_tv.setText(membersList[position].userEmail)

    }

    override fun getItemCount(): Int {
        return membersList.size
    }

    class MembersViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        var avatar_iv = itemView.findViewById<ImageView>(R.id.avatar_iv)
        var name_tv = itemView.findViewById<TextView>(R.id.name_tv)
        var email_tv = itemView.findViewById<TextView>(R.id.email_tv)

    }
}