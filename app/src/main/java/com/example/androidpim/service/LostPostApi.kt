package com.example.androidpim.service

import com.example.androidpim.models.Post
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

 interface LostPostApi {
    @GET("lost")
     fun GetAllLost(): Call<List<Post>>
   //  @GET("/found")
    // fun GetAllFound(): Call<PostResponse>

    companion object {
        fun create() : LostPostApi {
            return Retrofit.Builder()
                .baseUrl("http://192.168.1.187:3000/lostpost/lostFound/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(LostPostApi::class.java)
        }


    }
}

