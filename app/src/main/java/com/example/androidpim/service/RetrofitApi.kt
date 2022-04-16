package com.example.androidpim.service

import com.example.androidpim.models.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface RetrofitApi {

    @Multipart
    @POST("user/signup")
    fun userSignUp(
        @PartMap data : LinkedHashMap<String, RequestBody>,
        @Part profilePicture: MultipartBody.Part
    ) : Call<User>

    @Multipart
    @POST("club/signup")
    fun clubSignUp(
        @PartMap data : LinkedHashMap<String, RequestBody>,
        @Part clubLogo: MultipartBody.Part
    ) : Call<Club>


    @GET("/userByEmail/{email}")
    fun getUserByEmail(
        @Path("email") email:String,
    ) : Call<UserLoggedIn>


    @POST("auth")
    fun userLogin(
        @Body user: UserLoggedIn
    ):Call<UserLoggedIn>

    @POST("/auth/reset")
    fun sendResetCode(
        @Body email: UserReset):Call<UserResetResponse>

    @POST("/auth/reset/checkcode")
    fun checkCode(
        @Body check: Check):Call<CheckResponse>

    @PATCH("/auth/reset")
    fun changePasswordReset(
        @Body user: UserResetPassword):Call<User>

    @GET("user")
    fun GetAllUsers():Call<List<User>>

    /*
        @GET("article")
        fun GetAllArticles():Call<ArticlesReponse>
    */
    companion object {
        fun create() : RetrofitApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://10.0.2.2:3000/")
                .build()
            return retrofit.create(RetrofitApi::class.java)

        }
    }

}