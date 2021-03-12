package com.emarketing.bicycle.ui

import com.emarketing.bicycle.data.Material

interface EditMaterialView {
    fun onSuccess(data: Material)
    fun onFailer(message: String)
}