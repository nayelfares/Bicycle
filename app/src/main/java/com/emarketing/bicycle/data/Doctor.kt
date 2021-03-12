package com.emarketing.bicycle.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.ArrayList
@Parcelize
data class Doctor(
    val id:Int,
    val name:String,
    val photo:String,
    val address:String,
    val mobile:String,
    val email:String,
    val whatsapp:String,
    val specialization:String,
    val descritption:String?,
    val rating: Float?
    ): Parcelable

data class DoctorsListResult(val success:Boolean,val message:String,val data: ArrayList<Doctor>)