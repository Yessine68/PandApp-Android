package com.example.androidpim.adapters

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidpim.R
import com.example.androidpim.models.Document
import com.example.androidpim.models.Event

class DocumentAdapter(val documentList: List<Document>) : RecyclerView.Adapter<DocumentAdapter.DocumentViewHolder>() {
    lateinit var mSharedPref: SharedPreferences

    var mContext: Context? = null





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.document_signle, parent, false)
        this.mContext = parent.getContext()
        return DocumentViewHolder(view)
    }

    override fun onBindViewHolder(holder: DocumentViewHolder, position: Int) {
        holder.dotyperecycle.setText(documentList[position].documentType)
        holder.copiesNumberr.setText(documentList[position].numcopies.toString())
        holder.languagee.setText(documentList[position].docLanguage)
        holder.createdAt.setText(documentList[position].createdAT)
    }

    override fun getItemCount(): Int {
        return documentList.size
    }

    class DocumentViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        var dotyperecycle = itemView.findViewById<TextView>(R.id.dotyperecycle)
        var copiesNumberr = itemView.findViewById<TextView>(R.id.copiesNumberr)
        var languagee = itemView.findViewById<TextView>(R.id.languagee)
        var createdAt = itemView.findViewById<TextView>(R.id.createdAt)
    }
}