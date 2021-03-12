package com.emarketing.bicycle.ui

import com.emarketing.bicycle.data.Profile

interface MaterialView {
    fun onSuccess(profile: Profile)
    fun onFailed(message: String)
    fun onDeleteSuccess(message: String)
    fun onDeleteFailed(message: String)
}