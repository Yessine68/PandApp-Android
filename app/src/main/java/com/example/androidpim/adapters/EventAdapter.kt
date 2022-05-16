package com.example.androidpim.adapters

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidpim.R
import com.example.androidpim.models.Event
import com.example.androidpim.models.EventInt
import com.example.androidpim.models.Qrj
import com.example.androidpim.service.BASE_URL
import com.example.androidpim.service.RetrofitApi
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.marcoscg.dialogsheet.DialogSheet
import io.karn.notify.Notify
import io.karn.notify.NotifyCreator
import net.glxn.qrgen.core.scheme.VCard

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EventAdapter (val eventList: List<Event>) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {
    lateinit var mSharedPref: SharedPreferences

    var mContext: Context? = null

    private var qrImage : Bitmap? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_single, parent, false)
        this.mContext = parent.getContext();
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        mSharedPref = mContext?.getSharedPreferences("UserPref", Context.MODE_PRIVATE)!!
        val email: String = mSharedPref.getString("email", "zwayten").toString()

        val ppp = BASE_URL +"upload/download/"+eventList[position].banner

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
            val qrevent= inflatedView2?.findViewById<ImageView>(R.id.qrevent)
            val joinButton = inflatedView2?.findViewById<Button>(R.id.joinButton)












            var eventIntt = EventInt()
            eventIntt.postId = eventList[position]._id
            eventIntt.userEmail = email
            var exx = false
            val apijoinn = RetrofitApi.create().getEventIntByEmail(eventIntt.postId!!)
            apijoinn.enqueue(object : Callback<List<EventInt>>{
                override fun onResponse(call: Call<List<EventInt>>, response: Response<List<EventInt>>) {
                    if (response.isSuccessful){
                        //println(response.body()!![position].userEmail.toString())
                        for (i in 0 until response.body()!!.size)
                        {
                            exx = response.body()!![i].userEmail.toString() == email
                        }
                        if (exx == false){
                            joinButton?.setText("JOIN EVENT")
                        }
                        if (exx == true){
                            joinButton?.setText("LEAVE EVENT")
                        }
                    }
                }

                override fun onFailure(call: Call<List<EventInt>>, t: Throwable) {
                    println("failed")
                }

            })











            joinButton?.setOnClickListener { var eventInt = EventInt()
                eventInt.postId = eventList[position]._id
                eventInt.userEmail = email
                println("ooooo"+eventList[position]._id)

                var existedeja = false
                val apijoin = RetrofitApi.create().getEventIntByEmail(eventInt.postId!!)
                apijoin.enqueue(object : Callback<List<EventInt>>{
                    override fun onResponse(call: Call<List<EventInt>>, response: Response<List<EventInt>>) {
                        if (response.isSuccessful){

                    //println(response.body()!![position].userEmail.toString())
                        var eventIntId =""
                        var k = 0
                            for (i in 0 until response.body()!!.size)
                            {
                                existedeja = response.body()!![i].userEmail.toString() == email
                                k = i
                            }
                            if (existedeja == false){
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

                            if (existedeja == true){
                                eventIntId = response.body()!![k]._id.toString()
                                val apiiuser = RetrofitApi.create().leaveEvent(eventIntId)
                                apiiuser.enqueue(object : Callback<EventInt>{
                                    override fun onResponse(call: Call<EventInt>, response: Response<EventInt>) {
                                        //println(response.body()!!)
                                        println("khraj")
                                    }

                                    override fun onFailure(call: Call<EventInt>, t: Throwable) {
                                        println("failed")
                                    }

                                })
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<EventInt>>, t: Throwable) {
                        println("failed")
                    }

                })



            }

            //----qr code
            fun getTimeStamp(): String? {
                val tsLong = System.currentTimeMillis() / 1000
                val ts = tsLong.toString()

                return ts
            }
/*
            fun generateQRCode()
            {

                val vCard = VCard(eventTitleDetail?.text.toString())
                    .setAddress(eventList[position]._id)
                    .setPhoneNumber(email)
                    .setWebsite(descriptiondetail?.text.toString())
                qrImage =
                    net.glxn.qrgen.android.QRCode.from(vCard).bitmap()
                qrevent?.setImageBitmap(qrImage)

                /*
                else if(input_text.visibility == View.VISIBLE)
                {
                    qrImage = net.glxn.qrgen.android.QRCode.from(input_text.text.toString()).bitmap()
                    if(qrImage != null)
                    {
                        imageView_qrCode.setImageBitmap(qrImage)
                        btn_save.visibility = View.VISIBLE
                    }
                }
                */
            }


//----------------qr code

*/


            fun generateQj() {
                val data = Qrj(email, eventList[position]._id)
                println("qr email"+ data.userEmail)
                println("qr email"+ data.postId)
                var gson = Gson()
                var jsonString = gson.toJson(data)
                println(jsonString)

                val writer = QRCodeWriter()
                try {
                    val bitMatrix = writer.encode(jsonString,BarcodeFormat.QR_CODE, 512,512)
                    val width = bitMatrix.width
                    val height = bitMatrix.height
                    val bmp = Bitmap.createBitmap(width,height,Bitmap.Config.RGB_565)
                    for (x in 0 until width){
                        for (y in 0 until height) {
                            bmp.setPixel(x,y, if(bitMatrix[x,y]) Color.BLACK else Color.WHITE)
                        }
                    }
                    qrevent?.setImageBitmap(bmp)
                } catch (e:WriterException){
                    println("oooo")
                }

            }



            eventTitleDetail?.setText(eventList[position].title.toString())
            eventPlaceDetail?.setText(eventList[position].place.toString())
            eventTimeDetail?.setText(eventList[position].Time.toString())
            descriptiondetail?.setText(eventList[position].description.toString())
            generateQj()




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
        val qrevent = itemView.findViewById<ImageView>(R.id.qrevent)


    }
            /*
                            class Qrj(
                                var userEmail: String? = null,
                                val postId: String? = null
                            )

             */

}


