package com.emarketing.bicycle.vm

import android.content.Context
import com.emarketing.bicycle.api.MainAPIManager
import com.emarketing.bicycle.data.MaterialListResponse
import com.emarketing.bicycle.data.RequestInterface
import com.emarketing.bicycle.mvvm.BaseActivity
import com.emarketing.bicycle.ui.ShopingView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ShopingViewModel(val shopingView: ShopingView, val context: Context) {

    fun getMaterialList(user_id:String?){
        val apiManager= MainAPIManager().provideRetrofitInterface().create(RequestInterface::class.java)
        val registerVar  = apiManager.getMaterialList(BaseActivity.token,user_id)
        registerVar.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<MaterialListResponse> {
                override fun onComplete() { }
                override fun onSubscribe(d: Disposable) { }
                override fun onNext(t: MaterialListResponse) {
                    shopingView.onSuccess(t.data)
                }
                override fun onError(e: Throwable) {
                    shopingView.onFailer(e.message.toString())
                }
            })
    }
}