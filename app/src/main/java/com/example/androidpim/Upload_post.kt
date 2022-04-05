package com.example.androidpim

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.helper.widget.MotionEffect.TAG
import com.example.androidpim.service.uploadApi
import com.google.gson.JsonObject
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class upload_post : AppCompatActivity() {
    private val PICK_IMAGE_REQUEST = 1

    val REQUEST_iMAGE_CAPTURE =100
    lateinit var button : Button
    lateinit var cancel : Button
    lateinit var button_choose : Button
    lateinit var imageView: ImageView
    lateinit var imageViewBack: ImageView
    lateinit var buttonPost : Button



    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_post)
        buttonPost=findViewById(R.id.buttonPost)
        buttonPost.setOnClickListener {
            uploadImage();


        }
        imageViewBack=findViewById(R.id.imageViewBack)
        imageViewBack.setOnClickListener {
            finish()
        }
        imageView = findViewById(R.id.image_view)
        cancel=findViewById(R.id.buttonCancel)
        cancel.setOnClickListener {
            finish()
        }
        button=findViewById(R.id.buttonCamera)
        button.setOnClickListener {

            val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePicture,REQUEST_iMAGE_CAPTURE)
            }catch (e: ActivityNotFoundException){
                Toast.makeText(this,"ERROR:"+e.localizedMessage, Toast.LENGTH_SHORT).show()

            }
        }
        button_choose=findViewById(R.id.button_choose_image)
        button_choose.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)


        }

    }

    fun uploadImage(){


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        println("hi"+requestCode)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            imageUri = data?.data
            imageView.setImageURI(imageUri)

        } else if (requestCode == REQUEST_iMAGE_CAPTURE && resultCode == RESULT_OK) {
            val photo = data!!.extras!!["data"] as Bitmap?
            imageView.setImageBitmap(photo)
        }
        super.onActivityResult(requestCode, resultCode, data)

    }
    fun fileUpload(file: File) {
        val requestBody: RequestBody
        val body: MultipartBody.Part
        val mapRequestBody = LinkedHashMap<String, RequestBody>()
        val arrBody: MutableList<MultipartBody.Part> = ArrayList()
        requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        mapRequestBody["file\"; filename=\"" + file.getName()] = requestBody
        mapRequestBody["test"] =
            RequestBody.create(MediaType.parse("text/plain"), "gogogogogogogog")
        body = MultipartBody.Part.createFormData("fileName", file.getName(), requestBody)
        arrBody.add(body)
        val call: Call<JsonObject?>? =
            uploadApi.create().uploadFile(mapRequestBody, arrBody)
        if (call != null) {
            call.enqueue(object : Callback<JsonObject?> {
                override fun onResponse(call: Call<JsonObject?>?, response: Response<JsonObject?>) {
                    if (response.body() != null) {
                        println("res:"+response.body())
                    }
                }

                override fun onFailure(call: Call<JsonObject?>?, t: Throwable) {
                    t.message?.let { Log.e(TAG.toString() + "Err", it) }
                }
            })
        }
    }

}