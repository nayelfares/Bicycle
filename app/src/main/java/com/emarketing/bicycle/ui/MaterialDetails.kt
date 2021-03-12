package com.emarketing.bicycle.ui

import android.annotation.SuppressLint
import android.os.Bundle
import com.bumptech.glide.Glide
import com.emarketing.bicycle.R
import com.emarketing.bicycle.api.toUrl
import com.emarketing.bicycle.data.Material
import com.emarketing.bicycle.data.Profile
import com.emarketing.bicycle.mvvm.BaseActivity
import com.emarketing.bicycle.vm.MaterialViewModel
import kotlinx.android.synthetic.main.activity_material_details.*
import org.jetbrains.anko.doAsync

class MaterialDetails : BaseActivity(),MaterialView {
    lateinit var materialViewModel: MaterialViewModel
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_details)
        val materialDetails = intent.getParcelableExtra<Material>("material")!!
        materialViewModel= MaterialViewModel(this,this)
        loading()
        doAsync {
            materialViewModel.getProfile(materialDetails.user_id!!.toInt())
        }
        materialName.text = materialDetails.name
        details     .text = materialDetails.description
        price       .text = materialDetails.price
        Glide.with(this)
            .load(materialDetails.photo.toUrl())
            .into(photo)
    }

    override fun onSuccess(profile: Profile) {
        stopLoading()
        Glide.with(this)
            .load(profile.photo.toUrl())
            .into(profilePhoto)
        username.text = profile.name
        email.text    = profile.email
        phone.text    = profile.phone
        address.text  = profile.address

    }

    override fun onFailed(message: String) {
        showMessage(message)
    }
}