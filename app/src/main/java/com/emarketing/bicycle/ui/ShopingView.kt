package com.emarketing.bicycle.ui

import com.emarketing.bicycle.data.Material

interface ShopingView {
    fun onFailer(toString: String)
    fun onSuccess(categories: ArrayList<Material>)
}