package com.emarketing.bicycle.ui

import android.os.Bundle
import com.emarketing.bicycle.R
import com.emarketing.bicycle.data.Article
import com.emarketing.bicycle.mvvm.BaseActivity
import com.emarketing.bicycle.vm.ArticleAdapter
import com.emarketing.bicycle.vm.ShopingViewModel
import kotlinx.android.synthetic.main.activity_shoping.*

class ShopingActivity : BaseActivity(),ShopingView {
    var catId=1
    lateinit var shopingViewModel: ShopingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shoping)
        catId=intent.getIntExtra("catId",1)
        toolbar.text=intent.getStringExtra("catName")
        getMore.setOnRefreshListener { getMore.setRefreshing(false) }
        shopingViewModel= ShopingViewModel(this,this)
        loading()
        shopingViewModel.getCategories(catId)
    }

    override fun onFailer(message: String) {
        stopLoading()
        showMessage(message )
    }

    override fun onSuccess(categories: ArrayList<Article>) {
        stopLoading()
        content.adapter=ArticleAdapter(this,categories)
    }

}