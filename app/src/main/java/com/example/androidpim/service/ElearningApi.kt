package com.example.androidpim.service

import com.example.androidpim.models.Elearning
import com.example.androidpim.models.Post
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ElearningApi {


    @GET("elearning")
    fun GetAllElearning(): Call<List<Elearning>>

    companion object {
        fun create() : ElearningApi {
            return Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ElearningApi::class.java)
        }


    }
}