package com.emarketing.bicycle.ui

import com.emarketing.bicycle.data.Article

interface ShopingView {
    fun onFailer(toString: String)
    fun onSuccess(categories: ArrayList<Article>)
}