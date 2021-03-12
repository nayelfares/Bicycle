package com.emarketing.bicycle.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
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
    lateinit var materialDetails:Material
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_details)
         materialDetails = intent.getParcelableExtra<Material>("material")!!
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

        if (profile.id==BaseActivity.id) {
            edit.visibility = View.VISIBLE
            edit.setOnClickListener {
                val intent = Intent(this, EditMaterial::class.java)
                intent.putExtra("material", materialDetails)
                startActivityForResult(intent, 1000)
            }
        }
        if (profile.id==id|| isAdmin) {
            delete.visibility= View.VISIBLE
            delete.setOnClickListener {
                loading()
                materialViewModel.deleteMaterial(materialDetails.id)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==1000 && resultCode==Activity.RESULT_OK){
            materialDetails = data?.getParcelableExtra<Material>("edited")!!
            materialName.text = materialDetails.name
            details     .text = materialDetails.description
            price       .text = materialDetails.price
            Glide.with(this)
                .load(materialDetails.photo.toUrl())
                .into(photo)
        }
    }

    override fun onFailed(message: String) {
        showMessage(message)
    }

    override fun onDeleteSuccess(message: String) {
        showMessage(message)
        stopLoading()
        val resultIntent = Intent()
        resultIntent.putExtra("id", materialDetails.id)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    override fun onDeleteFailed(message: String) {
        stopLoading()
        showMessage(message)
    }
}