package com.emarketing.bicycle.ui

import com.emarketing.bicycle.data.Article

interface ArticalsView {
    fun onFailer(toString: String)
    fun onSuccess(categories: ArrayList<Article>)
}