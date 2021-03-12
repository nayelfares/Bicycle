package com.emarketing.bicycle.vm

import android.content.Context
import android.net.Uri
import com.emarketing.bicycle.R
import com.emarketing.bicycle.api.MainAPIManager
import com.emarketing.bicycle.data.RequestInterface
import com.emarketing.bicycle.data.Response
import com.emarketing.bicycle.mvvm.BaseActivity
import com.emarketing.bicycle.ui.AddMaterialView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class AddMaterialViewModel(val addMaterialView: AddMaterialView, val context: Context) {
    fun addMaterial(uri: Uri,name:String,description:String,price:Float){
        val apiManager= MainAPIManager().provideRetrofitInterface().create(RequestInterface::class.java)
        val picture = File(uri.path!!)
        val requestFile=picture.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("photo", picture.name, requestFile)
        val addVar  = apiManager.addMaterial(BaseActivity.token,body,name,description,price,BaseActivity.id)
        addVar.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<Response?> {
                override fun onComplete() { }
                override fun onSubscribe(d: Disposable) { }
                override fun onNext(t: Response) {
                    if (t.success!=false) {
                        addMaterialView.onSuccess(t.message)
                    }
                    else
                        addMaterialView.onFailer(t.message)
                }
                override fun onError(e: Throwable) {
                    addMaterialView.onFailer(context.resources.getString(R.string.check_intenet_connection))
                }
            })
    }
}