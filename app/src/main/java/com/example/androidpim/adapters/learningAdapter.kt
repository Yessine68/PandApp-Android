package com.example.androidpim.adapters

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class learningAdapter {


    class favoriteViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val favoritepic = itemView.findViewById<ImageView>(R.id.champPic)
        val nom = itemView.findViewById<TextView>(R.id.Nom)
        val dispo = itemView.findViewById<TextView>(R.id.dispo)
        val prix = itemView.findViewById<TextView>(R.id.prix)
        val btn_delete = itemView.findViewById<ImageButton>(R.id.btnLike)

    }
}