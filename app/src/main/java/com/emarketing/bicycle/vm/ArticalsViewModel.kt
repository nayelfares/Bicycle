package com.emarketing.bicycle.vm

import android.content.Context
import com.emarketing.bicycle.api.MainAPIManager
import com.emarketing.bicycle.data.ArticleResult
import com.emarketing.bicycle.data.RequestInterface
import com.emarketing.bicycle.mvvm.BaseActivity
import com.emarketing.bicycle.ui.ArticalsView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ArticalsViewModel(val articalsView: ArticalsView, val context: Context) {
    fun getCategories(categoryId:Int){
        val apiManager= MainAPIManager().provideRetrofitInterface().create(RequestInterface::class.java)
        val registerVar  = apiManager.getArticleList(BaseActivity.token,categoryId )
        registerVar.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ArticleResult> {
                override fun onComplete() { }
                override fun onSubscribe(d: Disposable) { }
                override fun onNext(t: ArticleResult) {
                        articalsView.onSuccess(t.data)
                }
                override fun onError(e: Throwable) {
                    articalsView.onFailer(e.message.toString())
                }
            })
    }
}