package com.emarketing.bicycle.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.ArrayList

@Parcelize
data class Material(
    val id: Int,
    val name: String,
    val description : String,
    val photo : String?,
    val price : String?,
    val user_id :String?
):Parcelable



data class MaterialListResponse(val success:Boolean,val message:String,val data :ArrayList<Material>)