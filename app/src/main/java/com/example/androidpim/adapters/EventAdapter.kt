package com.example.androidpim.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidpim.R
import com.example.androidpim.models.Event
import com.marcoscg.dialogsheet.DialogSheet

class EventAdapter (val eventList: List<Event>) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {


    var mContext: Context? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_single, parent, false)
        this.mContext = parent.getContext();
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {


        val ppp = "http://10.0.2.2:3000/upload/download/"+eventList[position].banner

        this.mContext?.let { Glide.with(it).load(Uri.parse(ppp)).into(holder.eventImage) }

         holder.eventTitle.setText(eventList[position].title.toString())

         holder.eventPlace.setText(eventList[position].place.toString())
        holder.eventTime.setText(eventList[position].Time.toString())
         holder.eventparticipants.setText("40")
        holder.itemView.setOnClickListener{
            val dialogSheetdetail = this.mContext?.let { it1 -> DialogSheet(it1, true) }
            dialogSheetdetail?.setView(R.layout.event_detail)
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