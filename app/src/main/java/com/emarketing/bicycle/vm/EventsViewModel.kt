package com.emarketing.bicycle.vm

import android.content.Context
import com.emarketing.bicycle.api.MainAPIManager
import com.emarketing.bicycle.data.EventListResponse
import com.emarketing.bicycle.data.MaterialListResponse
import com.emarketing.bicycle.data.RequestInterface
import com.emarketing.bicycle.data.Response
import com.emarketing.bicycle.mvvm.BaseActivity
import com.emarketing.bicycle.ui.EventsView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class EventsViewModel(val eventsView: EventsView,val context: Context) {
    fun getEventList(user_id:String?){
        val apiManager= MainAPIManager().provideRetrofitInterface().create(RequestInterface::class.java)
        val registerVar  = apiManager.getEventList(BaseActivity.token,user_id)
        registerVar.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<EventListResponse> {
                override fun onComplete() { }
                override fun onSubscribe(d: Disposable) { }
                override fun onNext(t: EventListResponse) {
                    eventsView.onSuccess(t.data)
                }
                override fun onError(e: Throwable) {
                    eventsView.onFailer(e.message.toString())
                }
            })
    }
}