package com.emarketing.bicycle.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.ArrayList

@Parcelize
data class Event (
    val id:Int,
    val name:String,
    val description:String,
    val objectives:String,
    val start_date:String,
    val end_date:String,
    val number_members:Int,
    var number_of_joining:Int,
    val user_id:String,
    var is_joined:Boolean
): Parcelable

data class EventListResponse(val success:Boolean,val message:String,val data : ArrayList<Event>)