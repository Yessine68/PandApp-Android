package com.example.androidpim.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.example.androidpim.R
import com.example.androidpim.adapters.OnboardingViewPagerAdapter5
import com.example.androidpim.fragments.OnboardingFragment
import com.example.androidpim.models.Club
import com.example.androidpim.models.ClubChat
import com.example.androidpim.models.User
import com.example.androidpim.service.RetrofitApi
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_onboarding_example4.btn_next_step
import kotlinx.android.synthetic.main.activity_onboarding_example4.btn_previous_step
import kotlinx.android.synthetic.main.activity_onboarding_example4.pageIndicator
import kotlinx.android.synthetic.main.activity_onboarding_example5.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URI

lateinit var registerClubShared: SharedPreferences

class OnboardingExample5 : AppCompatActivity() {

    private lateinit var mViewPager: ViewPager2
    private lateinit var btnBack: Button
    private lateinit var btnNext: Button


    private fun login(){

        registerClubShared = getSharedPreferences("RegisterClub", Context.MODE_PRIVATE)

        val clubName: String = registerClubShared.getString("clubName", "zwayten").toString()
        val login: String = registerClubShared.getString("login", "zwayten").toString()
        val password: String = registerClubShared.getString("password", "zwayten").toString()
        val clubOwner: String = registerClubShared.getString("clubOwner", "zwayten").toString()
        val description: String = registerClubShared.getString("description", "zwayten").toString()

        val pika = registerClubShared.getString("imageUrl", "zwayten").toString()
        if (pika == "null") {
            println("menghyr soura")
            var clubNoCapNoGun = Club()
            clubNoCapNoGun.clubLogo = "default.png"
            clubNoCapNoGun.clubName = clubName
            clubNoCapNoGun.clubOwner = clubOwner
            clubNoCapNoGun.description = description
            clubNoCapNoGun.login = login
            clubNoCapNoGun.password = password
            clubNoCapNoGun.social = true
            clubNoCapNoGun.verified = true



            val apiNoCapNoGun = RetrofitApi.create().clubNoCapNoGun(clubNoCapNoGun)
            apiNoCapNoGun.enqueue(object : Callback<Club>{
                override fun onResponse(call: Call<Club>, response: Response<Club>) {
                    if (response.isSuccessful){
                        var clubChat = ClubChat()
                        clubChat.clubName = clubNoCapNoGun.login
                        clubChat.esmElclub = clubNoCapNoGun.clubName
                        clubChat.clubImage = "default.png"
                        val apiClubChat = RetrofitApi.create().createChatRoom(clubChat)
                        apiClubChat.enqueue(object: Callback<ClubChat>{
                            override fun onResponse(call: Call<ClubChat>, response: Response<ClubChat>) {
                                println("chat room created")
                            }

                            override fun onFailure(call: Call<ClubChat>, t: Throwable) {
                                println("chat room not created")
                            }

                        })
                        finish()
                        registerClubShared.edit().clear().apply()

                    }
                }

                override fun onFailure(call: Call<Club>, t: Throwable) {
                    println("failed")
                }

            })
        }
        else {
            val selectedImageUri: Uri?= Uri.parse(registerClubShared.getString("imageUrl", "zwayten").toString())

            val stream = contentResolver.openInputStream(selectedImageUri!!)
            if(stream!=null){

                val request =
                    stream?.let { RequestBody.create("image/png".toMediaTypeOrNull(), it.readBytes()) } // read all bytes using kotlin extension
                val clubLogo = request?.let {
                    MultipartBody.Part.createFormData(
                        "file",
                        "file.png",
                        it
                    )
                }



                val apiInterface = RetrofitApi.create()
                val data: LinkedHashMap<String, RequestBody> = LinkedHashMap()



                data["clubName"] = clubName.toRequestBody(MultipartBody.FORM)
                data["login"] = login.toRequestBody(MultipartBody.FORM)
                data["password"] = password.toRequestBody(MultipartBody.FORM)
                data["clubOwner"] = clubOwner.toRequestBody(MultipartBody.FORM)
                data["description"] = description.toRequestBody(MultipartBody.FORM)

                if (clubLogo != null) {
                    println("++++++++++++++++++++++++++++++++++++"+clubLogo)
                    apiInterface.clubSignUp(data,clubLogo).enqueue(object:
                        Callback<Club> {
                        override fun onResponse(
                            call: Call<Club>,
                            response: Response<Club>
                        ) {
                            if(response.isSuccessful){
                                var clubChat = ClubChat()
                                clubChat.clubName = login

                                clubChat.esmElclub = clubName
                                println(clubLogo.toString())
                                println("taswira chat room"+response.body()!!.clubLogo)
                                clubChat.clubImage = response.body()!!.clubLogo
                                val apiClubChatt = RetrofitApi.create().createChatRoom(clubChat)
                                apiClubChatt.enqueue(object: Callback<ClubChat>{
                                    override fun onResponse(call: Call<ClubChat>, response: Response<ClubChat>) {
                                        println("chat room created")
                                    }

                                    override fun onFailure(call: Call<ClubChat>, t: Throwable) {
                                        println("chat room not created")
                                    }

                                })

                            } else {
                                Log.i("OnResponse not good", response.body().toString())
                            }
                        }

                        override fun onFailure(call: Call<Club>, t: Throwable) {

                            println("noooooooooooooooooo")
                        }

                    })
                }
            }
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        registerClubShared = getSharedPreferences("RegisterClub", Context.MODE_PRIVATE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_example5)
        mViewPager = viewPager2
        mViewPager.adapter = OnboardingViewPagerAdapter5(this, this)
        mViewPager.offscreenPageLimit = 1
        btnBack = btn_previous_step
        btnNext = btn_next_step
        mViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == 1) {
                    btnNext.text = getText(R.string.finish)
                    val oo: String = registerClubShared.getString("login", "zwayten").toString()
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
