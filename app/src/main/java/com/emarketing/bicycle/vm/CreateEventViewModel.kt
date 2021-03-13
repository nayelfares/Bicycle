package com.emarketing.bicycle.vm

import android.content.Context
import com.emarketing.bicycle.api.MainAPIManager
import com.emarketing.bicycle.data.EventListResponse
import com.emarketing.bicycle.data.RequestInterface
import com.emarketing.bicycle.data.Response
import com.emarketing.bicycle.mvvm.BaseActivity
import com.emarketing.bicycle.ui.CreateEventView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CreateEventViewModel(val createEventView: CreateEventView , val context: Context) {
    fun addEvent(name:String,startDate:String,endDate:String,description:String,objectives:String,member_number:Int,){
        val apiManager= MainAPIManager().provideRetrofitInterface().create(RequestInterface::class.java)
        val registerVar  = apiManager.addEvent(
            BaseActivity.token,name,startDate,endDate,
            description,objectives,member_number,BaseActivity.id
        )
        registerVar.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Response> {
                override fun onComplete() { }
                override fun onSubscribe(d: Disposable) { }
                override fun onNext(t: Response) {
                    createEventView.onSuccess(t.message)
                }
                override fun onError(e: Throwable) {
                    createEventView.onFailer(e.message.toString())
                }
            })
    }

    fun updateEvent(eventId:String,name:String,startDate:String,endDate:String,description:String,objectives:String,member_number:Int){
        val apiManager= MainAPIManager().provideRetrofitInterface().create(RequestInterface::class.java)
        val registerVar  = apiManager.updateEvent(
            BaseActivity.token,eventId,name,startDate,endDate,
            description,objectives,member_number
        )
        registerVar.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Response> {
                override fun onComplete() { }
                override fun onSubscribe(d: Disposable) { }
                override fun onNext(t: Response) {
                    createEventView.onSuccess(t.message)
                }
                override fun onError(e: Throwable) {
                    createEventView.onFailer(e.message.toString())
                }
            })
    }
}