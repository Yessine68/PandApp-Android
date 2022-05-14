package com.example.androidpim.service

import com.example.androidpim.models.Elearning
import com.example.androidpim.models.Post
import com.example.androidpim.models.User
import com.example.androidpim.models.chatList
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ElearningApi {


    @GET("elearning")
    fun GetAllElearning(): Call<List<Elearning>>

    @GET("elearning/chatroom")
    fun GetAllChat(): Call<List<chatList>>
    @POST("elearning/chatroom")
    fun addRoom(
        @Body data : LinkedHashMap<String, String>,
    ):Call<chatList>

    companion object {
        fun create() : ElearningApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ElearningApi::class.java)
        }


    }
}