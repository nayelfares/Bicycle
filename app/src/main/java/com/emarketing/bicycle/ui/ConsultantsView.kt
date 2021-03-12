package com.emarketing.bicycle.ui

import com.emarketing.bicycle.data.Doctor

interface ConsultantsView {
    fun onFailer(toString: String)
    fun onSuccess(doctors: ArrayList<Doctor>)
}