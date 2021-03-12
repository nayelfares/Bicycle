package com.emarketing.bicycle.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.ArrayList

@Parcelize
data class Article(
    val photos:ArrayList<String>,
    val status:String?,
    val category:String?,
    val sub_category:String?,
    @SerializedName("address")
    val title:String?,
    val body:String?
    ): Parcelable

data class ArticleResult(val success:Boolean,val message:String,val data: ArrayList<Article>)