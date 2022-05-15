package com.example.androidpim.service

import com.example.androidpim.models.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ElearningApi {


    @GET("elearning")
    fun GetAllElearning(): Call<List<Elearning>>

    @GET("elearning/chatroom/{email}")
    fun GetAllChat(
        @Path("email") email:String,
    ): Call<List<chatList>>
    @POST("elearning/chatroom")
    fun addRoom(
        @Body data : LinkedHashMap<String, String>,
    ):Call<chatList>
    @POST("message")
    fun addMessage(
        @Body data: com.example.androidpim.Message,
    ):Call<com.example.androidpim.Message>
    @GET("message/{roomName}")
    fun GetAllMessages(
        @Path("roomName") roomName:String,
    ): Call<List<com.example.androidpim.Message>>

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