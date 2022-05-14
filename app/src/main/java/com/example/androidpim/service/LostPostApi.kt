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
    @GET("mylostFound/lost/{id}")
     fun GetMyAllLost(@Path("id")id:String): Call<List<Post>>
    @GET("mylostFound/found/{id}")
     fun GetMyAllFound(@Path("id")id:String): Call<List<Post>>
    @GET("lostFound/lost/{id}")
    fun GetAllLost(@Path("id")id:String): Call<List<Post>>
    @GET("lostFound/found/{id}")
    fun GetAllFound(@Path("id")id:String): Call<List<Post>>

    @Multipart
     @POST("addpost")
     fun  lostAndFound(
         @PartMap data : LinkedHashMap<String, RequestBody>,
         @Part file: MultipartBody.Part
     ) : Call<Post>


     companion object {
         var baseUrl:BaseUrl = BaseUrl()

         fun create() : LostPostApi {
            return Retrofit.Builder()
                .baseUrl(baseUrl.baseApiUri+"lostpost/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(LostPostApi::class.java)
        }


    }
}

