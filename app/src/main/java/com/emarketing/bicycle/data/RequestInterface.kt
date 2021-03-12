package com.emarketing.bicycle.data

import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*


interface RequestInterface {
    @POST("user-register")
    fun register(
        @Query("name") name:String,
        @Query("email") file_id:String,
        @Query("phone") phone:String,
        @Query("password") password:String
    ): Observable<Response>

    @POST("user-login")
    fun login(
        @Query("email") file_id:String,
        @Query("password") password:String
    ): Observable<LoginResponse>

    @GET("article/search")
    fun getArticleList(
        @Header("Authorization")  token:String,
        @Query("category_id") categoryId:Int
    ): Observable<ArticleResult>

    @POST("password/create")
    fun resetPassword(
        @Query("email") email:String
    ): Observable<Response>

    @GET("doctor/search")
    fun getDoctorsList(
        @Header("Authorization")  token:String,
        @Query("address") address:String?=null,
        @Query("name") name:String?=null,
        @Query("specialization") specialization:String?=null
    ): Observable<DoctorsListResult>

    @POST("doctor/rating/add")
    fun rate(
        @Header("Authorization")  token:String,
        @Query("doctor_id") doctor_id:Int,
        @Query("value") value:Int,
        @Query("user_id") user_id:Int
    ): Observable<Response>

    @GET("user-profile_get")
    fun getProfile(
        @Header("Authorization")  token:String,
        @Query("id") id:Int
    ): Observable<ProfileResponse>

    @POST("change_photo")
    @Multipart
    fun upload(
        @Header("Authorization") authorization:String ,
        @Part photo: MultipartBody.Part,
        @Query("id") id:Int
    ): Observable<Response>

    @POST("user-profile_update")
    fun updateProfile(
        @Header("Authorization")  token:String,
        @Query("id") id:Int,
        @Query("name") name:String,
        @Query("phone") phone:String,
        @Query("dob") dob:String,
        @Query("details") details:String,
        @Query("password") password:String?=null
    ): Observable<Response>

}