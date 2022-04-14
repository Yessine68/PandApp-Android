package com.example.androidpim.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.androidpim.R
import com.example.androidpim.models.User
import com.example.androidpim.service.RetrofitApi
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class SignUpPro : AppCompatActivity() {
    lateinit var email: EditText
    lateinit var password: EditText
    //lateinit var passwordConfirm: EditText
    lateinit var identifant: EditText
    lateinit var phone: EditText
    lateinit var first: EditText
    lateinit var last: EditText
//    lateinit var classe: EditText
    //lateinit var description: EditText
    lateinit var save: Button


    private var selectedImageUri: Uri? = null
    var imagePicker: ImageView?=null
    private lateinit var fab: Button




    private fun login(identifant_text: String, email_text: String, password_text: String, phone_text: String, first_text: String, last_text: String, description_text: String){


        val stream = contentResolver.openInputStream(selectedImageUri!!)
        if(stream!=null){

            val request =
                stream?.let { RequestBody.create("image/png".toMediaTypeOrNull(), it.readBytes()) } // read all bytes using kotlin extension
            val profilePicture = request?.let {
                MultipartBody.Part.createFormData(
                    "file",
                    "file.png",
                    it
                )
            }





        Log.d("MyActivity", "on finish upload file")

        val apiInterface = RetrofitApi.create()
        val data: LinkedHashMap<String, RequestBody> = LinkedHashMap()

        data["identifant"] = identifant_text.toRequestBody(MultipartBody.FORM)
        data["email"] = email_text.toRequestBody(MultipartBody.FORM)
        data["password"] = password_text.toRequestBody(MultipartBody.FORM)
        data["phoneNumber"] = phone_text.toRequestBody(MultipartBody.FORM)
        data["FirstName"] = first_text.toRequestBody(MultipartBody.FORM)
        data["LastName"] = last_text.toRequestBody(MultipartBody.FORM)
        data["className"] = " classe_text".toRequestBody(MultipartBody.FORM)
        data["description"] = description_text.toRequestBody(MultipartBody.FORM)

        if (profilePicture != null) {
            println("++++++++++++++++++++++++++++++++++++"+profilePicture)
            apiInterface.userSignUp(data,profilePicture).enqueue(object:
                Callback<User> {
                override fun onResponse(
                    call: Call<User>,
                    response: Response<User>
                ) {
                    if(response.isSuccessful){
                        Log.i("onResponse goooood", response.body().toString())

                    } else {
                        Log.i("OnResponse not good", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {

                    println("noooooooooooooooooo")
                }

            })
        }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_pro)
        supportActionBar?.hide();


        email = findViewById(R.id.email)
        //println("########################################"+email.text.toString())
        password = findViewById(R.id.password)
        //passwordConfirm = findViewById(R.id.confirm)
        identifant = findViewById(R.id.identifant)
        phone = findViewById(R.id.phone)
        first = findViewById(R.id.firstname)
        last = findViewById(R.id.lastName)
        //classe = findViewById(R.id.classe)
        //description = findViewById(R.id.desc)
        save = findViewById(R.id.signup_btn)

        fab = findViewById(R.id.pickButton)
        imagePicker = findViewById(R.id.imagepick)

        fab.setOnClickListener(

            View.OnClickListener {
            ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        })





        save.setOnClickListener {
            val email_text = email.text.toString().trim()
            val password_text = password.text.toString().trim()
            //var passwordConfirm_text = passwordConfirm.text.toString()
            val identifant_text = identifant.text.toString().trim()
            val phone_text = phone.text.toString().trim()
            val first_text = first.text.toString().trim()
            val last_text = last.text.toString().trim()
            //var classe_text = classe.text.toString()
            //var description_text = description.text.toString()
            val description_text = "aaaaaaaaaaaaaaaaaa"
            println("########################################"+email.text.toString())
            login(identifant_text, email_text, password_text, phone_text, first_text, last_text, description_text)

        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == ImagePicker.REQUEST_CODE){
            selectedImageUri = data?.data
            imagePicker?.setImageURI(selectedImageUri)

        }
    }





}