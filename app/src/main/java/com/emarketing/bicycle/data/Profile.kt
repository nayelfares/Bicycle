package com.emarketing.bicycle.data


data class Profile(
    val id:Int,
    val name:String,
    val photo:String,
    val phone:String,
    val email:String,
    val address:String?,
    val dob:String
)

data class ProfileResponse(val success:Boolean,val message:String,val data: Profile)