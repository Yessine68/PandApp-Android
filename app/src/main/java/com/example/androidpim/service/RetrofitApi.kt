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

    @POST("user/signup/google")
    fun usergooglesignup(
        @Body user: User):Call<User>

    @POST("club")
    fun clubNoCapNoGun(
        @Body club: Club):Call<Club>

    @Multipart
    @POST("club/signup")
    fun clubSignUp(
        @PartMap data : LinkedHashMap<String, RequestBody>,
        @Part clubLogo: MultipartBody.Part
    ) : Call<Club>

    @Multipart
    @POST("event/")
    fun postEvent(
        @PartMap data : LinkedHashMap<String, RequestBody>,
        @Part banner: MultipartBody.Part
    ) : Call<Event>



    @GET("user/userByEmail/{email}")
    fun getUserByEmail(
        @Path("email") email:String,
    ) : Call<List<UserLoggedIn>>

    @GET("eventInt/eventIntById/{postId}")
    fun getEventIntByEmail(
        @Path("postId") postId:String,
    ) : Call<List<EventInt>>


    @POST("auth")
    fun userLogin(
        @Body user: UserLoggedIn
    ):Call<UserLoggedIn>

    @POST("document")
    fun requestDoc(
        @Body doc: Document
    ):Call<Document>

    @POST("EventInt")
    fun joinEvent(
        @Body eventInt: EventInt
    ):Call<EventInt>

    @DELETE("EventInt/{id}")
    fun leaveEvent(
        @Path("id") id:String,
    ):Call<EventInt>

    @POST("authClub")
    fun clubLogin(
        @Body club: ClubLoggedIn
    ):Call<ClubLoggedIn>

    @POST("/auth/reset")
    fun sendResetCode(
        @Body email: UserReset):Call<UserResetResponse>

    @POST("/auth/reset/checkcode")
    fun checkCode(
        @Body check: Check):Call<CheckResponse>

    @PATCH("/auth/reset")
    fun changePasswordReset(
        @Body password: UserResetPassword):Call<User>

    @PATCH("/user/{email}")
    fun updateProfileUser(
        @Path("email") email:String,
        @Body user: User):Call<User>

    @GET("user")
    fun GetAllUsers():Call<List<User>>

    @GET("event")
    fun GetEvents():Call<List<Event>>

    @GET("document")
    fun GetDocuments():Call<List<Document>>

    /*
        @GET("article")
        fun GetAllArticles():Call<ArticlesReponse>
    */
    companion object {

        fun create() : RetrofitApi {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(RetrofitApi::class.java)

        }
    }

}