package com.example.androidpim.view

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidpim.R


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


}