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



    @POST("password/create")
    fun resetPassword(
        @Query("email") email:String
    ): Observable<Response>

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

    @GET("material")
    fun getMaterialList(
        @Header("Authorization")  token:String,
        @Query("id") id:String?
    ): Observable<MaterialListResponse>

    @GET("profile_get")
    fun getUser(
        @Header("Authorization")  token:String,
        @Query("id") id:Int
    ): Observable<UserResponse>

    @POST("material")
    @Multipart
    fun addMaterial(
        @Header("Authorization") authorization:String ,
        @Part photo: MultipartBody.Part,
        @Query("name") name:String,
        @Query("description") description:String,
        @Query("price") price:Float,
        @Query("id") id:Int
    ): Observable<Response>

    @POST("update_materail")
    @Multipart
    fun editMaterial(
        @Header("Authorization") authorization:String ,
        @Query("id") material_id:Int,
        @Part photo: MultipartBody.Part?=null,
        @Query("name") name:String,
        @Query("description") description:String,
        @Query("price") price:Float
    ): Observable<MaterialResponse>

    @POST("update_materail")
    fun editNoPhotoMaterial(
        @Header("Authorization") authorization:String ,
        @Query("id") material_id:Int,
        @Query("name") name:String,
        @Query("description") description:String,
        @Query("price") price:Float
    ): Observable<MaterialResponse>

    @DELETE("material/{material_id}")
    fun deleteMaterial(
        @Header("Authorization") authorization:String,
        @Path("material_id") material_id:Int,
    ):Observable<Response>

    @GET("event")
    fun getEventList(
        @Header("Authorization")  token:String,
        @Query("id") id:String?
    ): Observable<EventListResponse>

    @POST("event")
    fun addEvent(
        @Header("Authorization") authorization:String ,
        @Query("name") name:String,
        @Query("start_date") start_date:String,
        @Query("end_date") end_date:String,
        @Query("description") description:String,
        @Query("objectives") objectives:String,
        @Query("number_members") number_members:Int,
        @Query("user_id") id:Int
    ): Observable<Response>

    @PUT("event/{id}")
    fun updateEvent(
        @Header("Authorization") authorization:String ,
        @Path("id") id:String,
        @Query("name") name:String,
        @Query("start_date") start_date:String,
        @Query("end_date") end_date:String,
        @Query("description") description:String,
        @Query("objectives") objectives:String,
        @Query("number_members") number_members:Int
    ): Observable<Response>

    @DELETE("event/{id}")
    fun deleteEvent(
        @Header("Authorization") authorization:String ,
        @Path("id") id:String
    ): Observable<Response>

    @POST("join")
    fun joinEvent(
        @Header("Authorization") authorization:String ,
        @Query("event_id") event_id:String,
        @Query("join") join:Boolean,
        @Query("user_id") user_id:String,
    ): Observable<Response>
}