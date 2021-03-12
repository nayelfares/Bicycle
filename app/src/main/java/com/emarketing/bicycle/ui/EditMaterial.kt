package com.emarketing.bicycle.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.bumptech.glide.Glide
import com.emarketing.bicycle.R
import com.emarketing.bicycle.api.toUrl
import com.emarketing.bicycle.data.Material
import com.emarketing.bicycle.mvvm.BaseActivity
import com.emarketing.bicycle.vm.EditMaterialViewModel
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_edit_material.*

class EditMaterial : BaseActivity(),EditMaterialView {
    lateinit var editMaterialViewModel: EditMaterialViewModel
    lateinit var materialDetails:Material
    var photoUri: Uri?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_material)
        editMaterialViewModel=EditMaterialViewModel(this,this)
        materialDetails = intent.getParcelableExtra<Material>("material")!!
        materialName.setText( materialDetails.name)
        details.setText( materialDetails.description)
        price.setText( materialDetails.price)
        Glide.with(this)
            .load(materialDetails.photo.toUrl())
            .into(photo)

        camera.setOnClickListener {
            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(this)
        }

        submit.setOnClickListener {
                loading()
                try {
                    val price= price.text.toString().toFloat()
                    editMaterialViewModel.editMaterial(
                        photoUri,
                        materialDetails.id,
                        materialName.text.toString(),
                        details.text.toString(),
                        price
                    )
                }catch (e: Exception){
                    editMaterialViewModel.editMaterial(
                        photoUri,
                        materialDetails.id,
                        materialName.text.toString(),
                        details.text.toString(),
                        0f
                    )
                }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                photoUri = result.uri
                Glide.with(this)
                    .load(photoUri)
                    .into(photo)
            }
        }
    }

    override fun onSuccess(data: Material) {
        stopLoading()
        val resultIntent = Intent()
        resultIntent.putExtra("edited", data)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    override fun onFailer(message: String) {
        stopLoading()
        showMessage(message)
    }
}