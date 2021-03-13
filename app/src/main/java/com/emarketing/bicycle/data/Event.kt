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
    val number_members:String,
    val user_id:String
): Parcelable

data class EventListResponse(val success:Boolean,val message:String,val data : ArrayList<Event>)