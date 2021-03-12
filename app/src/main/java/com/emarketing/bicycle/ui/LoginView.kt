package com.emarketing.bicycle.ui

interface LoginView {
    fun loginSuccess(token:String,id:Int)
    fun loginFailed(message:String)

    fun resetSuccess(message: String)
}