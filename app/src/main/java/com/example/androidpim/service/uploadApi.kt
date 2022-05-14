package com.example.androidpim.service

import com.example.androidpim.models.Post
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface uploadApi {
@Multipart
@POST("upload/file")
open fun uploadFile(
    @Part file: List<MultipartBody.Part?>?
): Call<JsonObject?>?
    @GET("download")
    fun GetAllArticles():Call<Post>


    companion object {
        var baseUrl:BaseUrl = BaseUrl()
        fun create() : uploadApi {
           return Retrofit.Builder()
               .baseUrl(BASE_URL)
               .addConverterFactory(GsonConverterFactory.create())
                .build()
            .create(uploadApi::class.java)

        }


    }

}
