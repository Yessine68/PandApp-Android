package com.example.androidpim.service

import com.example.androidpim.models.Parking
import com.example.androidpim.models.Post
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ParkingApi {
    @GET("parking")
    fun getAllParking(): Call<List<Parking>>
    @POST("parking")
    fun postParking(
        @Body data : Any,
    ):Call<Any>
     companion object {

         fun create() : ParkingApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ParkingApi::class.java)
        }


    }
}

