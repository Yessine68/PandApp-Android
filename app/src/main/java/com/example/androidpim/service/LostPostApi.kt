package com.example.androidpim.service

import com.example.androidpim.models.Post
import com.example.androidpim.models.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface LostPostApi {
    @GET("lostFound/lost")
     fun GetAllLost(): Call<List<Post>>
    @GET("lostFound/found")
     fun GetAllFound(): Call<List<Post>>
     @Multipart
     @POST("addpost")
     fun  lostAndFound(
         @PartMap data : LinkedHashMap<String, RequestBody>,
         @Part file: MultipartBody.Part
     ) : Call<Post>


     companion object {
        fun create() : LostPostApi {
            return Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/lostpost/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(LostPostApi::class.java)
        }


    }
}

