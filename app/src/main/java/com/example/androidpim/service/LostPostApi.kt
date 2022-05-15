package com.example.androidpim.service

import com.example.androidpim.models.Post
import com.example.androidpim.models.User
import com.example.androidpim.models.chatList
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface LostPostApi {
    @GET("lostpost/lostFound/lost")
    fun GetAllLost(): Call<List<Post>>
    @GET("lostpost/lostFound/found")
    fun GetAllFound(): Call<List<Post>>

    @Multipart
     @POST("lostpost/addpost")
     fun  lostAndFound(
         @PartMap data : LinkedHashMap<String, RequestBody>,
         @Part file: MultipartBody.Part
     ) : Call<Post>
    @PATCH("lostpost/{id}")
    fun changeState(
        @Path("id") id:String,
        @Body data : Post,
    ):Call<Boolean>


     companion object {

         fun create() : LostPostApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(LostPostApi::class.java)
        }


    }
}

