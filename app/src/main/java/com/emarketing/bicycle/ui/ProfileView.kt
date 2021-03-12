package com.emarketing.bicycle.ui

import android.net.Uri
import com.emarketing.bicycle.data.Profile

interface ProfileView {
    fun getProfileSuccess(profile: Profile)
    fun getProfileFailed(message:String)

    fun updatePhotoOnSuccess(message:String,uri: Uri)
    fun updatePhotoOnSFailer(message:String)
    fun updateProfileSuccess(message: String)
    fun updateProfileFailed(message: String)
}