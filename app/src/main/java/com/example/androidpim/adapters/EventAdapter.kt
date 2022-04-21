package com.example.androidpim.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidpim.R
import com.example.androidpim.models.Event

class EventAdapter (val eventList: List<Event>) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {






    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_single, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
         holder.eventTitle.setText(eventList[position].title.toString())

         holder.eventPlace.setText(eventList[position].place.toString())
        holder.eventTime.setText(eventList[position].Time.toString())
         holder.eventparticipants.setText("40")
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


    }
}