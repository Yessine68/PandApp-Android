package com.example.androidpim.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.androidpim.R
import com.example.androidpim.models.Post
import com.example.androidpim.models.User
import com.example.androidpim.service.LostPostApi
import com.example.androidpim.service.RetrofitApi
import com.github.dhaval2404.imagepicker.ImagePicker
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class upload_post : AppCompatActivity() {

    lateinit var cancel : Button
    lateinit var button_choose : Button
    lateinit var imageView: ImageView
    lateinit var imageViewBack: ImageView
    lateinit var mSharedPref: SharedPreferences

    lateinit var buttonPost : Button
    lateinit var objet: EditText
      lateinit var place: EditText


    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_post)
        objet= findViewById(R.id.objet)
        place= findViewById(R.id.place)
        mSharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE)

        var route = intent.getStringExtra("data")
        val user_id: String = mSharedPref.getString("LastName", "zwayten").toString() + "" + mSharedPref.getString("FirstName", "zwayten").toString()
        val email =mSharedPref.getString("email", "zwayten").toString().trim()
        println("ghassenklai "+user_id)
        buttonPost=findViewById(R.id.buttonPost)
        buttonPost.setOnClickListener {
            val objet_text = objet.text.toString().trim()
            val place_text = place.text.toString().trim()
            val publisheId = user_id
            val type= route!!
            println("ghassen klai "+user_id)
            addPost(email,publisheId,type,objet_text,place_text)
            finish()
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

        button_choose=findViewById(R.id.button_choose_image)
        button_choose.setOnClickListener(

            View.OnClickListener {
                ImagePicker.with(this)
                    .crop()	    			//Crop image(Optional), Check Customization for more option
                    .compress(1024)			//Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                    .start()
            })

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == ImagePicker.REQUEST_CODE){
            selectedImageUri = data?.data
            imageView?.setImageURI(selectedImageUri)

        }
    }
    private fun addPost(email:String,publisheId: String, type: String, objet: String, place: String){


        val stream = contentResolver.openInputStream(selectedImageUri!!)
        if(stream!=null){

            val request =
                stream?.let { RequestBody.create("image/png".toMediaTypeOrNull(), it.readBytes()) } // read all bytes using kotlin extension
            val imagePost = request?.let {
                MultipartBody.Part.createFormData(
                    "file",
                    "file.png",
                    it
                )
            }
            Log.d("MyActivity", "on finish upload file")
            val apiInterface = LostPostApi.create()
            val data: LinkedHashMap<String, RequestBody> = LinkedHashMap()
            data["publisheId"] = publisheId.toRequestBody(MultipartBody.FORM)
            data["type"] = type.toRequestBody(MultipartBody.FORM)
            data["objet"] = objet.toRequestBody(MultipartBody.FORM)
            data["place"] = place.toRequestBody(MultipartBody.FORM)
            data["email"] = email.toRequestBody(MultipartBody.FORM)


            if (imagePost != null) {
                println("++++++++++++++++++++++++++++++++++++"+imagePost)
                apiInterface.lostAndFound(data,imagePost).enqueue(object:
                    Callback<Post> {
                    override fun onResponse(
                        call: Call<Post>,
                        response: Response<Post>
                    ) {
                        if(response.isSuccessful){
                            Log.i("onResponse goooood", response.body().toString())

                        } else {
                            Log.i("OnResponse not good", response.body().toString())
                        }
                    }

                    override fun onFailure(call: Call<Post>, t: Throwable) {

                        println("noooooooooooooooooo")
                    }

                })
            }
        }
    }



}

private fun <T> Call<T>.enqueue(callback: Callback<Post>) {

}
