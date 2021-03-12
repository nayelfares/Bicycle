package com.emarketing.bicycle.data


data class User(
    val id:Int,
    val name:String,
    val photo:String,
    val address:String,
    val mobile:String,
    val email:String,
    val specialization:String,
    val descritption:String?,
    val rating: Float?
)

data class UserResponse(val success:Boolean,val message:String,val data: User)