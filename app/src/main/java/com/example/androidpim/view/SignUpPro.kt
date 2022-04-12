package com.example.androidpim.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.androidpim.R
import com.example.androidpim.models.UserLoggedIn
import com.example.androidpim.service.RetrofitApi
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_pro)
        supportActionBar?.hide();






        email = findViewById(R.id.email)
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

        fab.setOnClickListener(View.OnClickListener {
            ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        })


        var email_text = email.text.toString()
        var password_text = password.text.toString()
        //var passwordConfirm_text = passwordConfirm.text.toString()
         var identifant_text = identifant.text.toString()
        var phone_text = phone.text.toString()
        var first_text = first.text.toString()
        var last_text = last.text.toString()
        //var classe_text = classe.text.toString()
        //var description_text = description.text.toString()
        var description_text = "aaaaaaaaaaaaaaaaaa"



        val apiInterface = RetrofitApi.create()
        val data: LinkedHashMap<String, RequestBody> = LinkedHashMap()
        data["identifant"] = RequestBody.create(MultipartBody.FORM, identifant_text)
        data["email"] = RequestBody.create(MultipartBody.FORM, email_text)
        data["password"] = RequestBody.create(MultipartBody.FORM, password_text)

        data["phoneNumber"] = RequestBody.create(MultipartBody.FORM, phone_text)
        data["FirstName"] = RequestBody.create(MultipartBody.FORM, first_text)
        data["LastName"] = RequestBody.create(MultipartBody.FORM, last_text)
        //data["className"] = RequestBody.create(MultipartBody.FORM, classe_text)
        data["description"] = RequestBody.create(MultipartBody.FORM, description_text)



        save.setOnClickListener {
            val stream = contentResolver.openInputStream(selectedImageUri!!)
            val request =
                stream?.let { RequestBody.create("image/*".toMediaTypeOrNull(), it.readBytes()) } // read all bytes using kotlin extension
            val profilePicture = request?.let {
                MultipartBody.Part.createFormData(
                    "file",
                    "image.jpeg",
                    it
                )
            }
            if (profilePicture != null) {

                apiInterface.userSignUp(data,profilePicture).enqueue(object: Callback<UserLoggedIn> {
                    override fun onResponse(call: Call<UserLoggedIn>, response: Response<UserLoggedIn>) {
                        if(response.isSuccessful){
                            println("jawek 9ron9os")
                        }
                    }

                    override fun onFailure(call: Call<UserLoggedIn>, t: Throwable) {
                        println("nooooooooooooooooooooooooo")
                    }

                })
            }
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