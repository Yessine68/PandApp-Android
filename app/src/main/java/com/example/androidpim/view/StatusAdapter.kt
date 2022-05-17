package com.example.androidpim.view

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidpim.R
import com.example.androidpim.models.Club
import com.example.androidpim.models.ClubMembers
import com.example.androidpim.models.User
import com.example.androidpim.service.BASE_URL
import com.example.androidpim.service.RetrofitApi
import com.marcoscg.dialogsheet.DialogSheet
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class StatusAdapter(val activity: Context, val statusList: ArrayList<Club>) : RecyclerView.Adapter<StatusAdapter.ViewHolder>() {
    var mContext: Context? = null
    lateinit var mSharedPref: SharedPreferences
    var etatJoin: String ="none"
    var idHmed: String = ""
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0?.context).inflate(R.layout.adapter_status_layout, p0, false)
        this.mContext = activity;
        mSharedPref = mContext?.getSharedPreferences("UserPref", Context.MODE_PRIVATE)!!

        return ViewHolder(view);
    }

    override fun getItemCount(): Int {
        return statusList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {



        p0.name?.text = statusList[p1].clubName
        p0.name.visibility = View.VISIBLE
        val ppp = BASE_URL +"upload/download/"+statusList[p1].clubLogo
        Glide.with(activity)
            .load(ppp)
            .into(p0.photo)
        val dialogSheetdetail = this.mContext?.let { it1 -> DialogSheet(it1, true) }
        dialogSheetdetail?.setView(R.layout.club_view_signle)
        val inflatedView2 = dialogSheetdetail?.inflatedView



        val imageProfileInsta = inflatedView2?.findViewById<ImageView>(R.id.imageProfileInsta)
        val clubNameInsta = inflatedView2?.findViewById<TextView>(R.id.clubNameInsta)
        val descriptioninsta = inflatedView2?.findViewById<TextView>(R.id.descriptioninsta)
        val joinInsta = inflatedView2?.findViewById<Button>(R.id.joinInsta)
        val loggedAs = mSharedPref.getString("lastlogged", "user").toString()
        if(loggedAs == "club"){
            joinInsta?.isVisible = false
        }
        if(loggedAs == "user"){
            joinInsta?.isVisible = true
        }

        val apiCheck = RetrofitApi.create().getClubMemberByClubNameAndUserEmail(mSharedPref.getString("email", "zwayten").toString(),
            statusList[p1].login.toString()
        )

        apiCheck.enqueue(object : Callback<List<ClubMembers>> {
            override fun onResponse(
                call: Call<List<ClubMembers>>,
                response: Response<List<ClubMembers>>
            ) {
                if(response.isSuccessful){
                    if(response.body()!!.size != 0){
                        if(response.body()!![0].state ==  false){
                            etatJoin = "pending"
                            idHmed = response.body()!![0]._id.toString()
                            joinInsta?.setText("Cancel Request")

                        }
                        if(response.body()!![0].state ==  true){
                            etatJoin = "existe"
                            idHmed = response.body()!![0]._id.toString()
                            joinInsta?.setText("leave")
                        }
                    }
                    if(response.body()!!.size == 0){
                        etatJoin= "none"
                        joinInsta?.setText("Join")
                    }
                }
            }

            override fun onFailure(call: Call<List<ClubMembers>>, t: Throwable) {
                println("mochkla")
            }

        })







        joinInsta?.setOnClickListener {
            if(etatJoin == "none"){
                val clubMembers = ClubMembers()
                clubMembers.userEmail = mSharedPref.getString("email", "zwayten").toString()
                clubMembers.userName = mSharedPref.getString("FirstName", "zwayten").toString() + " "+ mSharedPref.getString("LastName", "zwayten").toString()
                clubMembers.memberPicture = mSharedPref.getString("profilePicture", "default.png").toString()
                clubMembers.state = false
                clubMembers.clubName = statusList[p1].login
                clubMembers.name = statusList[p1].clubName
                val apiJoinClubRojla = RetrofitApi.create().joinClubRojla(clubMembers)
                apiJoinClubRojla.enqueue(object : Callback<ClubMembers>{
                    override fun onResponse(call: Call<ClubMembers>, response: Response<ClubMembers>) {
                        if(response.isSuccessful){
                            joinInsta?.setText("Cancel Request")
                            etatJoin = "pending"
                        }
                    }

                    override fun onFailure(call: Call<ClubMembers>, t: Throwable) {
                        println("ooo")
                    }

                })
            }

            if(etatJoin == "pending" || etatJoin == "existe"){
                val apiDeleteMember = RetrofitApi.create().deleteMember(idHmed)
                apiDeleteMember.enqueue( object : Callback<ClubMembers>{
                    override fun onResponse(
                        call: Call<ClubMembers>,
                        response: Response<ClubMembers>
                    ) {
                        if(response.isSuccessful){
                            joinInsta?.setText("Join")
                            etatJoin = "none"
                        }

                    }

                    override fun onFailure(call: Call<ClubMembers>, t: Throwable) {
                        println("oo")
                    }

                } )

            }

        }

        if (imageProfileInsta != null) {
            Glide.with(activity)
                .load(ppp)
                .into(imageProfileInsta)
        }
        clubNameInsta?.setText(statusList[p1].clubName)
        descriptioninsta?.setText(statusList[p1].description)


        p0.itemView.setOnClickListener{
            dialogSheetdetail?.show()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.profile_name)
        val photo = itemView.findViewById<ImageView>(R.id.profile_img)
        //val add = itemView.findViewById<ImageView>(R.id.ic_add_img)
    }
}