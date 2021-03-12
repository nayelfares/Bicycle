package com.emarketing.bicycle.vm

import android.content.Context
import com.emarketing.bicycle.api.MainAPIManager
import com.emarketing.bicycle.data.LoginResponse
import com.emarketing.bicycle.data.ProfileResponse
import com.emarketing.bicycle.data.RequestInterface
import com.emarketing.bicycle.data.Response
import com.emarketing.bicycle.mvvm.BaseActivity
import com.emarketing.bicycle.ui.MaterialView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MaterialViewModel(val materialView: MaterialView,val context: Context) {
    fun getProfile(userId:Int){
        val apiManager= MainAPIManager().provideRetrofitInterface().create(RequestInterface::class.java)
        val registerVar  = apiManager.getProfile(BaseActivity.token,userId)
        registerVar.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ProfileResponse> {
                override fun onComplete() { }
                override fun onSubscribe(d: Disposable) { }
                override fun onNext(t: ProfileResponse) {
                    if (t.success)
                        materialView.onSuccess(t.data)
                    else
                        materialView.onFailed(t.message)
                }
                override fun onError(e: Throwable) {
                    materialView.onFailed(e.message.toString())
                }
            })
    }

    fun deleteMaterial(id: Int) {
        val apiManager= MainAPIManager().provideRetrofitInterface().create(RequestInterface::class.java)
        val registerVar  = apiManager.deleteMaterial(BaseActivity.token,id)
        registerVar.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Response> {
                override fun onComplete() { }
                override fun onSubscribe(d: Disposable) { }
                override fun onNext(t: Response) {
                    if (t.success)
                        materialView.onDeleteSuccess(t.message)
                    else
                        materialView.onDeleteFailed(t.message)
                }
                override fun onError(e: Throwable) {
                    materialView.onDeleteFailed(e.message.toString())
                }
            })
    }
}