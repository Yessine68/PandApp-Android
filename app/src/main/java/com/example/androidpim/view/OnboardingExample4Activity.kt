package com.example.androidpim.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.example.androidpim.R
import com.example.androidpim.adapters.OnboardingViewPagerAdapter4
import com.example.androidpim.fragments.OnboardingFragment
import com.example.androidpim.models.EventInt
import com.example.androidpim.models.User
import com.example.androidpim.service.RetrofitApi
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_onboarding_example4.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URI

lateinit var registerShared: SharedPreferences

class OnboardingExample4Activity : AppCompatActivity() {

    private lateinit var mViewPager: ViewPager2
    private lateinit var btnBack: Button
    private lateinit var btnNext: Button


    private fun login() {

        registerShared = getSharedPreferences("Register", Context.MODE_PRIVATE)
        val pika = registerShared.getString("imageUrl", "zwayten").toString()
        println("el taswira el mokerza "+ pika)
        val identifant_text: String =
            registerShared.getString("identifant", "zwayten").toString()
        val email_text: String = registerShared.getString("email", "zwayten").toString()
        val password_text: String = registerShared.getString("password", "zwayten").toString()
        val phone_text: String = registerShared.getString("phone", "zwayten").toString()
        val first_text: String = registerShared.getString("firstname", "zwayten").toString()
        val last_text: String = registerShared.getString("lastName", "zwayten").toString()
        val description_text: String =
            registerShared.getString("description", "zwayten").toString()
        val classe_text: String = registerShared.getString("className", "zwayten").toString()

        if (pika == "null") {
            println("menghyr soura")
            var userNoCapNoGun = User()

            userNoCapNoGun.identifant = identifant_text
            userNoCapNoGun.FirstName = first_text
            userNoCapNoGun.LastName = last_text
            userNoCapNoGun.className = classe_text
            userNoCapNoGun.description = description_text
            userNoCapNoGun.email = email_text
            userNoCapNoGun.password = password_text
            userNoCapNoGun.phoneNumber = phone_text.toInt()
            userNoCapNoGun.profilePicture = "default.png"
            userNoCapNoGun.social = true
            userNoCapNoGun.verified = true
            var exx = false
            val apiNoCapNoGun = RetrofitApi.create().usergooglesignup(userNoCapNoGun)
            apiNoCapNoGun.enqueue(object : Callback<User>{
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful){
                        finish()
                        registerShared.edit().clear().apply()

                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    println("failed")
                }

            })
        } else {
            val selectedImageUri: Uri? =
                Uri.parse(registerShared.getString("imageUrl", "zwayten").toString())
        val stream = contentResolver.openInputStream(selectedImageUri!!)
        if (stream != null) {

            val request =
                stream?.let {
                    RequestBody.create(
                        "image/png".toMediaTypeOrNull(),
                        it.readBytes()
                    )
                } // read all bytes using kotlin extension
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
            data["className"] = classe_text.toRequestBody(MultipartBody.FORM)
            data["description"] = description_text.toRequestBody(MultipartBody.FORM)

            if (profilePicture != null) {
                println("++++++++++++++++++++++++++++++++++++" + profilePicture)
                apiInterface.userSignUp(data, profilePicture).enqueue(object :
                    Callback<User> {
                    override fun onResponse(
                        call: Call<User>,
                        response: Response<User>
                    ) {
                        if (response.isSuccessful) {
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
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        registerShared = getSharedPreferences("Register", Context.MODE_PRIVATE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_example4)
        mViewPager = viewPager
        mViewPager.adapter = OnboardingViewPagerAdapter4(this, this)
        mViewPager.offscreenPageLimit = 1
        btnBack = btn_previous_step
        btnNext = btn_next_step
        mViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == 1) {
                    btnNext.text = getText(R.string.finish)
                    val oo: String = registerShared.getString("firstname", "zwayten").toString()
                    println("jinaaaaa men b3id 3chiri"+ oo)
                } else {
                    btnNext.text = getText(R.string.next)
                }
            }

            override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}
            override fun onPageScrollStateChanged(arg0: Int) {}
        })
        TabLayoutMediator(pageIndicator, mViewPager) { _, _ -> }.attach()

        btnNext.setOnClickListener {
            if (getItem() > mViewPager.childCount - 1) {

                login()
                //apiiiiiiii


                val intent = Intent(applicationContext, LoginPro::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                mViewPager.setCurrentItem(getItem() + 1, true)
            }
        }

        btnBack.setOnClickListener {
            if (getItem() == 0) {
                finish()
            } else {
                mViewPager.setCurrentItem(getItem() - 1, true)
            }
        }
    }

    private fun getItem(): Int {
        return mViewPager.currentItem
    }
}
