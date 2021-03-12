package com.emarketing.bicycle.ui

import com.emarketing.bicycle.data.Doctor

interface AddMaterialView {
    fun onFailer(message: String)
    fun onSuccess(message: String)
}