package com.emarketing.bicycle.vm

import android.content.Context
import android.net.Uri
import com.emarketing.bicycle.R
import com.emarketing.bicycle.api.MainAPIManager
import com.emarketing.bicycle.data.ProfileResponse
import com.emarketing.bicycle.data.RequestInterface
import com.emarketing.bicycle.data.Response
import com.emarketing.bicycle.mvvm.BaseActivity
import com.emarketing.bicycle.ui.ProfileView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class ProfileViewModel(val profileView: ProfileView,val context: Context) {
    fun getProfile(){
        val apiManager= MainAPIManager().provideRetrofitInterface().create(RequestInterface::class.java)
        val registerVar  = apiManager.getProfile(BaseActivity.token,BaseActivity.id)
        registerVar.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ProfileResponse> {
                override fun onComplete() { }
                override fun onSubscribe(d: Disposable) { }
                override fun onNext(t: ProfileResponse) {
                    if (t.success!=false)
                        profileView.getProfileSuccess(t.data)
                    else
                        profileView.getProfileFailed(t.message)
                }
                override fun onError(e: Throwable) {
                    profileView.getProfileFailed(context.resources.getString(R.string.check_intenet_connection))
                }
            })
    }

    fun updateProfie(name:String,phone:String,dob:String,details:String,password:String){
        val apiManager= MainAPIManager().provideRetrofitInterface().create(RequestInterface::class.java)
        var registerVar  = apiManager.updateProfile(
            BaseActivity.token,
            BaseActivity.id,
            name,phone,dob,details,password
        )
        if (password.trim()=="")
            registerVar  = apiManager.updateProfile(
                BaseActivity.token,
                BaseActivity.id,
                name,phone,dob,details
            )
        registerVar.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Response> {
                override fun onComplete() { }
                override fun onSubscribe(d: Disposable) { }
                override fun onNext(t: Response) {
                    if (t.success!=false)
                        profileView.updateProfileSuccess(t.message)
                    else
                        profileView.updateProfileFailed(t.message)
                }
                override fun onError(e: Throwable) {
                    profileView.updateProfileFailed(context.resources.getString(R.string.check_intenet_connection))
                }
            })
    }

    fun updatePhoto(uri: Uri){
        val apiManager= MainAPIManager().provideRetrofitInterface().create(RequestInterface::class.java)
        val picture = File(uri.path!!)
        var requestFile=picture.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("photo", picture.name, requestFile)
        val loginVar  = apiManager.upload(BaseActivity.token,body,BaseActivity.id)
        loginVar?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<Response?> {
                override fun onComplete() { }
                override fun onSubscribe(d: Disposable) { }
                override fun onNext(t: Response) {
                    if (t.success!=false) {
                        profileView.updatePhotoOnSuccess(t.message,uri)
                    }
                    else
                        profileView.updatePhotoOnSFailer(t.message)
                }
                override fun onError(e: Throwable) {
                    profileView.updatePhotoOnSFailer(context.resources.getString(R.string.check_intenet_connection))
                }
            })
    }
}