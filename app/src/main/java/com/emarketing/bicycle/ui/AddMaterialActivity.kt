package com.emarketing.bicycle.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.bumptech.glide.Glide
import com.emarketing.bicycle.R
import com.emarketing.bicycle.mvvm.BaseActivity
import com.emarketing.bicycle.vm.AddMaterialViewModel
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_add_material.*
import java.lang.Exception

class AddMaterialActivity : BaseActivity(),AddMaterialView {
    lateinit var addMaterialViewModel: AddMaterialViewModel
    var photoUri: Uri?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_material)
        addMaterialViewModel= AddMaterialViewModel(this,this)
        camera.setOnClickListener {
            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(this)
        }
        submit.setOnClickListener {
            if (photoUri!=null) {
                loading()
                try {
                  val price= price.text.toString().toFloat()
                    addMaterialViewModel.addMaterial(
                        photoUri!!,
                        materialName.text.toString(),
                        details.text.toString(),
                        price
                    )
                }catch (e:Exception){
                    addMaterialViewModel.addMaterial(
                        photoUri!!,
                        materialName.text.toString(),
                        details.text.toString(),
                        0f
                    )
                }

            }else{
                showMessage(resources.getString(R.string.add_material_photo))
            }
        }
    }

    override fun onFailer(message: String) {
        stopLoading()
        showMessage(message )
    }


    override fun onSuccess(message: String) {
        stopLoading()
        showMessage(message )
        val resultIntent = Intent()
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
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

}