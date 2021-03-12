package com.emarketing.bicycle.vm

import android.content.Context
import com.emarketing.bicycle.api.MainAPIManager
import com.emarketing.bicycle.data.LoginResponse
import com.emarketing.bicycle.data.RequestInterface
import com.emarketing.bicycle.data.Response
import com.emarketing.bicycle.ui.LoginView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LoginViewModel(val loginView: LoginView, val context: Context) {

    fun login(email:String,password:String){
        val apiManager= MainAPIManager().provideRetrofitInterface().create(RequestInterface::class.java)
        val registerVar  = apiManager.login(email,password)
        registerVar.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<LoginResponse> {
                override fun onComplete() { }
                override fun onSubscribe(d: Disposable) { }
                override fun onNext(t: LoginResponse) {
                    if (t.success!=false)
                        loginView.loginSuccess(t.data.token,t.data.id,t.data.is_admin)
                    else
                        loginView.loginFailed(t.message)
                }
                override fun onError(e: Throwable) {
                    loginView.loginFailed(e.message.toString())
                }
            })
    }
    fun resetPassword(email:String){
        val apiManager= MainAPIManager().provideRetrofitInterface().create(RequestInterface::class.java)
        val registerVar  = apiManager.resetPassword(email)
        registerVar.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Response> {
                override fun onComplete() { }
                override fun onSubscribe(d: Disposable) { }
                override fun onNext(t: Response) {
                    if (t.success!=false)
                        loginView.resetSuccess(t.message)
                    else
                        loginView.loginFailed(t.message)
                }
                override fun onError(e: Throwable) {
                    loginView.loginFailed(e.message.toString())
                }
            })
    }

}