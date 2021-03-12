package com.emarketing.bicycle.ui

import android.annotation.SuppressLint
import android.app.ActionBar
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.view.Window
import com.bumptech.glide.Glide
import com.emarketing.bicycle.R
import com.emarketing.bicycle.api.toUrl
import com.emarketing.bicycle.data.*
import com.emarketing.bicycle.mvvm.BaseActivity
import com.emarketing.bicycle.vm.ProfileViewModel
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_profile_details.*
import kotlinx.android.synthetic.main.activity_profile_details.submit
import kotlinx.android.synthetic.main.pick_date.*

class ProfileDetails : BaseActivity() ,ProfileView{
    lateinit var profileViewModel: ProfileViewModel
    lateinit var profile: Profile
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_details)
        profileViewModel= ProfileViewModel(this,this)
        loading()
        profileViewModel.getProfile()
        photo.setOnClickListener {
                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(this)
        }
        cameraContainer.setOnClickListener{photo.callOnClick()}
        camera.setOnClickListener{photo.callOnClick()}
        birthDate.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    val dialog =
                        Dialog(this, R.style.Theme_Design_BottomSheetDialog)
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    dialog.setCancelable(false)
                    dialog.setContentView(R.layout.pick_date)
                    dialog.cancelOptions.setOnClickListener {
                        dialog.dismiss()
                    }
                    dialog.submit.setOnClickListener {
                        val pirthdateString=dialog.datePicker.year.toString()+"-"+(dialog.datePicker.month+1).toString()+"-"+dialog.datePicker.dayOfMonth.toString()
                        birthDate.setText(pirthdateString)
                        dialog.dismiss()
                    }

                    val window: Window = dialog.window!!
                    window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT)
                    dialog.show()
                }
            }

            v?.onTouchEvent(event) ?: true
        }

        submit.setOnClickListener {
            loading()
            profileViewModel.updateProfie(
                username.text.toString(),
                phone.text.toString(),
                birthDate.text.toString(),
                details.text.toString(),
                password.text.toString()
            )
        }
    }

    override fun getProfileSuccess(profile: Profile) {
        stopLoading()
        this.profile=profile
        Glide.with(this)
            .load(profile.photo.toUrl())
            .into(photo)
        username.setText(profile.name)
        details.setText(profile.details)
        email.setText(profile.email)
        phone.setText(profile.mobile)
        birthDate.setText(profile.dob)
    }

    override fun getProfileFailed(message: String) {
        stopLoading()
        showMessage(message)
        finish()
    }

    override fun updatePhotoOnSuccess(message: String, uri: Uri) {
        photo.setImageURI(uri)
        stopLoading()
        showMessage(message)
    }

    override fun updatePhotoOnSFailer(message: String) {
        stopLoading()
        showMessage(message)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri: Uri = result.uri
                loading()
                profileViewModel.updatePhoto(resultUri)
            }
        }
    }

    override fun updateProfileFailed(message: String) {
        stopLoading()
        showMessage(message)
    }

    override fun updateProfileSuccess(message: String) {
        stopLoading()
        showMessage(message)
        finish()
    }
}