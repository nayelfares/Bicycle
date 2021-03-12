package com.emarketing.bicycle.data

import java.util.ArrayList

data class Event (
    val id:Int,
    val name:String,
    val description:String,
    val start_date:String,
    val end_date:String,
    val number_members:String,
    val user_id:String
)

data class EventListResponse(val success:Boolean,val message:String,val data : ArrayList<Event>)