package com.example.androidpim.service

import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap


interface uploadApi {
@Multipart
@POST("upload/file")
open fun uploadFile(
    @Part file: List<MultipartBody.Part?>?
): Call<JsonObject?>?


    companion object {
        fun create() : uploadApi {
           return Retrofit.Builder()
               .baseUrl("https://glacial-taiga-36886.herokuapp.com/")
               .addConverterFactory(GsonConverterFactory.create())
                .build()
            .create(uploadApi::class.java)

        }


    }
}
