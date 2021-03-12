package com.emarketing.bicycle.vm

import android.content.Context
import android.net.Uri
import com.emarketing.bicycle.R
import com.emarketing.bicycle.api.MainAPIManager
import com.emarketing.bicycle.data.MaterialResponse
import com.emarketing.bicycle.data.RequestInterface
import com.emarketing.bicycle.data.Response
import com.emarketing.bicycle.mvvm.BaseActivity
import com.emarketing.bicycle.ui.EditMaterialView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class EditMaterialViewModel(val editMaterialView: EditMaterialView,val context: Context) {
    fun editMaterial(uri: Uri?,materiaId:Int, name:String, description:String, price:Float){
        var body:MultipartBody.Part?=null
        val apiManager= MainAPIManager().provideRetrofitInterface().create(RequestInterface::class.java)
        if (uri!=null) {
            val picture = File(uri.path!!)
            val requestFile = picture.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            body = MultipartBody.Part.createFormData("photo", picture.name, requestFile)
        }
        val addVar:Observable<MaterialResponse>
        if (body!=null)
            addVar  = apiManager.editMaterial(BaseActivity.token,materiaId,body,name,description,price)
        else
            addVar  =apiManager.editNoPhotoMaterial(BaseActivity.token,materiaId,name,description,price)
        addVar.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<MaterialResponse?> {
                override fun onComplete() { }
                override fun onSubscribe(d: Disposable) { }
                override fun onNext(t: MaterialResponse) {
                    if (t.success!=false) {
                        editMaterialView.onSuccess(t.data)
                    }
                    else
                        editMaterialView.onFailer(t.message)
                }
                override fun onError(e: Throwable) {
                    editMaterialView.onFailer(context.resources.getString(R.string.check_intenet_connection))
                }
            })
    }
}