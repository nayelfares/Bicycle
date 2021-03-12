package com.emarketing.bicycle.vm

import android.content.Context
import com.emarketing.bicycle.api.MainAPIManager
import com.emarketing.bicycle.data.Response
import com.emarketing.bicycle.data.RequestInterface
import com.emarketing.bicycle.ui.RegisterView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RegisterViewModel(val registerView: RegisterView, val context: Context) {

    fun register(name:String,email:String,phone:String,password:String){
        val apiManager= MainAPIManager().provideRetrofitInterface().create(RequestInterface::class.java)
        val registerVar  = apiManager.register(name,email,phone,password)
        registerVar.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Response> {
                override fun onComplete() { }
                override fun onSubscribe(d: Disposable) { }
                override fun onNext(t: Response) {
                    if (t.success!=false)
                        registerView.onSuccess(t.message)
                    else
                        registerView.onFailer(t.message)
                }
                override fun onError(e: Throwable) {
                    registerView.onFailer(e.message.toString())
                }
            })
    }
}