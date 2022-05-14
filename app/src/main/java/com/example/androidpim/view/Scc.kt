package com.example.androidpim.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.androidpim.R
import com.example.androidpim.models.EventInt
import com.example.androidpim.service.RetrofitApi
import com.google.zxing.integration.android.IntentIntegrator
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Scc : AppCompatActivity() {
    private var qrScanIntegrator: IntentIntegrator? = null
    lateinit var btnScan: Button
    lateinit var nameKey: TextView
    lateinit var name: TextView
    lateinit var site_name_key: TextView
    lateinit var site_name: TextView
    lateinit var showQRScanner: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scc)
        btnScan = findViewById(R.id.btnScan)
        nameKey = findViewById(R.id.nameKey)
        name = findViewById(R.id.name)
        site_name_key = findViewById(R.id.site_name_key)
        site_name = findViewById(R.id.site_name)
        showQRScanner = findViewById(R.id.showQRScanner)

        btnScan.setOnClickListener { performAction() }
        setupScanner()
    }

    private fun performAction() {
        // Code to perform action when button is clicked.
        qrScanIntegrator?.initiateScan()
    }

    private fun setupScanner() {
        qrScanIntegrator = IntentIntegrator(this)
        qrScanIntegrator?.setOrientationLocked(false)

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            // If QRCode has no data.
            if (result.contents == null) {
                Toast.makeText(this, getString(R.string.result_not_found), Toast.LENGTH_LONG).show()
            } else {
                // If QRCode contains data.
                try {
                    // Converting the data to json format
                    val obj = JSONObject(result.contents)

                    // Show values in UI.
                    name.text = obj.getString("postId")
                    site_name.text = obj.getString("userEmail")
                    //Toast.makeText(this, "ooooooooooooooooooooooooooooooo", Toast.LENGTH_LONG).show()
                    var eventInt = EventInt()
                    eventInt.postId = obj.getString("postId")
                    eventInt.userEmail = obj.getString("userEmail")

                    var existedeja = false
                    val apijoin = RetrofitApi.create().getEventIntByEmail(eventInt.postId!!)
                    apijoin.enqueue(object : Callback<List<EventInt>> {
                        override fun onResponse(call: Call<List<EventInt>>, response: Response<List<EventInt>>) {
                            if (response.isSuccessful){
                                //println(response.body()!![position].userEmail.toString())
                                var eventIntId =""
                                var k = 0
                                for (i in 0 until response.body()!!.size)
                                {
                                    existedeja = response.body()!![i].userEmail.toString() == obj.getString("userEmail")
                                    k = i
                                }
                                if (existedeja == false){
                                    showQRScanner.text = "Join event"
                                    showQRScanner.setOnClickListener {
                                        val apiuser = RetrofitApi.create().joinEvent(eventInt)
                                        apiuser.enqueue(object : Callback<EventInt> {
                                            override fun onResponse(call: Call<EventInt>, response: Response<EventInt>) {
                                                println(response.body()!!)
                                            }

                                            override fun onFailure(call: Call<EventInt>, t: Throwable) {
                                                println("failed")
                                            }

                                        })
                                    }



                                }

                                if (existedeja == true){

                                    showQRScanner.text = "Leave event"
                                    showQRScanner.setOnClickListener { eventIntId = response.body()!![k]._id.toString()
                                        val apiiuser = RetrofitApi.create().leaveEvent(eventIntId)
                                        apiiuser.enqueue(object : Callback<EventInt> {
                                            override fun onResponse(call: Call<EventInt>, response: Response<EventInt>) {
                                                //println(response.body()!!)
                                                println("khraj")
                                            }

                                            override fun onFailure(call: Call<EventInt>, t: Throwable) {
                                                println("failed")
                                            }

                                        }) }

                                }
                            }
                        }

                        override fun onFailure(call: Call<List<EventInt>>, t: Throwable) {
                            println("failed")
                        }

                    })

                } catch (e: JSONException) {
                    e.printStackTrace()

                    // Data not in the expected format. So, whole object as toast message.
                    Toast.makeText(this, "Scan a valid QR code", Toast.LENGTH_LONG).show()
                    //Toast.makeText(this,"ùùùùù", Toast.LENGTH_LONG).show()
                    Log.i("info", result.contents)
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }



}