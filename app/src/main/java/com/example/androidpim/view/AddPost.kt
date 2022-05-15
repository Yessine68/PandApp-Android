package com.example.androidpim.view

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.androidpim.R
import com.example.androidpim.models.Event
import com.example.androidpim.service.RetrofitApi
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.datepicker.MaterialDatePicker.Builder.datePicker
import com.google.android.material.internal.ContextUtils.getActivity
import kotlinx.android.synthetic.main.fragment_home_pro.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.jetbrains.anko.activityManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddPost : AppCompatActivity() {

    lateinit var imageViewBackadd: ImageView
    var fab: ImageView?=null
    lateinit var titleadd: EditText
    lateinit var placeadd: EditText
    lateinit var descriptionadd: EditText
    lateinit var datePicker1: DatePicker
    lateinit var postAdd:Button
    lateinit var mSharedPref: SharedPreferences
    private var selectedImageUri: Uri? = null


    private fun login(titleadd_text: String, placeadd_text: String,datePicker1_text: String, descriptionadd_text: String){

        val stream = contentResolver.openInputStream(selectedImageUri!!)
        if(stream!=null){

            val request =
                stream?.let { RequestBody.create("image/png".toMediaTypeOrNull(), it.readBytes()) } // read all bytes using kotlin extension
            val banner = request?.let {
                MultipartBody.Part.createFormData(
                    "file",
                    "file.png",
                    it
                )
            }


            val publisher = mSharedPref.getString("login", "zwayten").toString()
            val typeadd = "Event"
            Log.d("MyActivity", "on finish upload file")
            val apiInterface = RetrofitApi.create()
            val data: LinkedHashMap<String, RequestBody> = LinkedHashMap()
            data["publisheId"] = publisher.toRequestBody(MultipartBody.FORM)
            data["place"] = placeadd_text.toRequestBody(MultipartBody.FORM)
            data["Time"] = datePicker1_text.toRequestBody(MultipartBody.FORM)
            data["description"] = descriptionadd_text.toRequestBody(MultipartBody.FORM)
            data["title"] = titleadd_text.toRequestBody(MultipartBody.FORM)
            data["type"] = typeadd.toRequestBody(MultipartBody.FORM)

            if (banner != null) {
                println("++++++++++++++++++++++++++++++++++++"+banner)
                apiInterface.postEvent(data,banner).enqueue(object:
                    Callback<Event> {
                    override fun onResponse(call: Call<Event>, response: Response<Event>) {
                        if(response.isSuccessful){
                            Log.i("ahawa", response.body().toString())

                        } else {
                            Log.i("niet", response.body().toString())
                        }
                    }

                    override fun onFailure(call: Call<Event>, t: Throwable) {

                        println("lel")
                    }

                })
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addpost)

        mSharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE)

        imageViewBackadd = findViewById(R.id.imageViewBackadd)
        fab = findViewById(R.id.eventImageadd)
        titleadd = findViewById(R.id.titleadd)
        placeadd = findViewById(R.id.placeadd)
        descriptionadd = findViewById(R.id.descriptionadd)
        datePicker1 = findViewById(R.id.datePicker1)
        postAdd = findViewById(R.id.postAdd)

        fab?.setOnClickListener(

            View.OnClickListener {
                ImagePicker.with(this)
                    .crop()	    			//Crop image(Optional), Check Customization for more option
                    .compress(1024)			//Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                    .start()
            })

        postAdd.setOnClickListener {
            //.text.toString().trim()
            val day: Int = datePicker1.getDayOfMonth()
            val month: Int = datePicker1.getMonth() + 1
            val year: Int = datePicker1.getYear()
            val fullDate = day.toString()+"-"+month.toString()+"-"+year.toString()
            val titleadd_text =  titleadd.text.toString().trim()
            val placeadd_text = placeadd.text.toString().trim()
            val descriptionadd_text = descriptionadd.text.toString().trim()
            login(titleadd_text, placeadd_text,fullDate, descriptionadd_text)
        }
        imageViewBackadd.setOnClickListener{
            finish()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == ImagePicker.REQUEST_CODE){
            selectedImageUri = data?.data
            fab?.setImageURI(selectedImageUri)
        }
    }
}