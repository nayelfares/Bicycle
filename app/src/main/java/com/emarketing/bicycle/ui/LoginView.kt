package com.emarketing.bicycle.ui

interface LoginView {
    fun loginSuccess(token:String,id:Int,isAdmin:Boolean)
    fun loginFailed(message:String)

    fun resetSuccess(message: String)
}