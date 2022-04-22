package com.example.androidpim.adapters

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidpim.R
import com.example.androidpim.models.Elearning

class learningAdapter (val coursesList: MutableList<Elearning>) :  RecyclerView.Adapter<learningAdapter.cours>(){




    class cours (itemView: View) : RecyclerView.ViewHolder(itemView){
        val className = itemView.findViewById<TextView>(R.id.text)
        val module = itemView.findViewById<TextView>(R.id.text2)
        val courseName = itemView.findViewById<TextView>(R.id.text3)
        val btn_show = itemView.findViewById<Button>(R.id.btnShow)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cours {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.eleatning_model, parent, false)
        return cours(view)      }

    override fun onBindViewHolder(holder: cours, position: Int) {
        val className = coursesList[position].className
        val module = coursesList[position].module
        val courseName = coursesList[position].courseName
        holder.className.text = className
        holder.module.text = module
        holder.courseName.text = courseName
        holder.btn_show.setOnClickListener {


        }     }

    override fun getItemCount(): Int {
        return coursesList.size
    }
}