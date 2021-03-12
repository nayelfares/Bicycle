package com.emarketing.bicycle.vm

import android.content.Context
import com.emarketing.bicycle.api.MainAPIManager
import com.emarketing.bicycle.data.DoctorsListResult
import com.emarketing.bicycle.data.RequestInterface
import com.emarketing.bicycle.mvvm.BaseActivity
import com.emarketing.bicycle.ui.ConsultantsView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ConsultantsViewModel(val consultantsView: ConsultantsView, val context: Context) {
    fun getDoctorsList(){
        val apiManager= MainAPIManager().provideRetrofitInterface().create(RequestInterface::class.java)
        val registerVar  = apiManager.getDoctorsList(BaseActivity.token )
        registerVar.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<DoctorsListResult> {
                override fun onComplete() { }
                override fun onSubscribe(d: Disposable) { }
                override fun onNext(t: DoctorsListResult) {
                    consultantsView.onSuccess(t.data)
                }
                override fun onError(e: Throwable) {
                    consultantsView.onFailer(e.message.toString())
                }
            })
    }
}