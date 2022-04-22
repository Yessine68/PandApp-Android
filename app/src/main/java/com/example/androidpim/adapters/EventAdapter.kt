package com.example.androidpim.adapters

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidpim.R
import com.example.androidpim.models.Event
import com.example.androidpim.models.EventInt
import com.example.androidpim.service.RetrofitApi
import com.marcoscg.dialogsheet.DialogSheet
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventAdapter (val eventList: List<Event>) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {
    lateinit var mSharedPref: SharedPreferences

    var mContext: Context? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_single, parent, false)
        this.mContext = parent.getContext();
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        mSharedPref = mContext?.getSharedPreferences("UserPref", Context.MODE_PRIVATE)!!
        val email: String = mSharedPref.getString("email", "zwayten").toString()

        val ppp = "http://10.0.2.2:3000/upload/download/"+eventList[position].banner

        this.mContext?.let { Glide.with(it).load(Uri.parse(ppp)).into(holder.eventImage) }

         holder.eventTitle.setText(eventList[position].title.toString())

         holder.eventPlace.setText(eventList[position].place.toString())
        holder.eventTime.setText(eventList[position].Time.toString())
         holder.eventparticipants.setText("40")
        holder.itemView.setOnClickListener{


            val dialogSheetdetail = this.mContext?.let { it1 -> DialogSheet(it1, true) }
            dialogSheetdetail?.setView(R.layout.event_detail)
            val inflatedView2 = dialogSheetdetail?.inflatedView
            val eventImagedetail = inflatedView2?.findViewById<ImageView>(R.id.eventImagedetail)
            val eventTitleDetail = inflatedView2?.findViewById<TextView>(R.id.eventTitleDetail)
            val eventTimeDetail= inflatedView2?.findViewById<TextView>(R.id.eventTimeDetail)
            val eventPlaceDetail= inflatedView2?.findViewById<TextView>(R.id.eventPlaceDetail)
            val descriptiondetail= inflatedView2?.findViewById<TextView>(R.id.descriptiondetail)
            val joinButton = inflatedView2?.findViewById<Button>(R.id.joinButton)
            joinButton?.setOnClickListener { var eventInt = EventInt()
                eventInt.postId = eventList[position]._id
                eventInt.userEmail = email
                val apiuser = RetrofitApi.create().joinEvent(eventInt)
                apiuser.enqueue(object : Callback<EventInt>{
                    override fun onResponse(call: Call<EventInt>, response: Response<EventInt>) {
                        println(response.body()!!)
                    }

                    override fun onFailure(call: Call<EventInt>, t: Throwable) {
                       println("failed")
                    }

                })
            }



            eventTitleDetail?.setText(eventList[position].title.toString())
            eventPlaceDetail?.setText(eventList[position].place.toString())
            eventTimeDetail?.setText(eventList[position].Time.toString())
            descriptiondetail?.setText(eventList[position].description.toString())

            this.mContext?.let {
                if (eventImagedetail != null) {
                    Glide.with(it).load(Uri.parse(ppp)).into(eventImagedetail)
                }
            }
            dialogSheetdetail?.show()
        }


    }

    override fun getItemCount(): Int {
        return eventList.size
    }


    class EventViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        //val favoritepic = itemView.findViewById<ImageView>(R.id.champPic)
        val eventTitle = itemView.findViewById<TextView>(R.id.eventTitle)
        val eventPlace = itemView.findViewById<TextView>(R.id.eventPlace)
        val eventTime = itemView.findViewById<TextView>(R.id.eventTime)
        val eventparticipants = itemView.findViewById<TextView>(R.id.eventparticipants)
        val eventImage = itemView.findViewById<ImageView>(R.id.eventImage)


    }
}