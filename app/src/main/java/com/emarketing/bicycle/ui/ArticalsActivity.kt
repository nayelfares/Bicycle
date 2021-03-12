package com.emarketing.bicycle.ui

import android.os.Bundle
import com.emarketing.bicycle.R
import com.emarketing.bicycle.data.Article
import com.emarketing.bicycle.mvvm.BaseActivity
import com.emarketing.bicycle.vm.ArticleAdapter
import com.emarketing.bicycle.vm.ArticalsViewModel
import kotlinx.android.synthetic.main.activity_articals.*

class ArticalsActivity : BaseActivity(),ArticalsView {
    var catId=1
    lateinit var articalsViewModel: ArticalsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articals)
        catId=intent.getIntExtra("catId",1)
        toolbar.text=intent.getStringExtra("catName")
        getMore.setOnRefreshListener { getMore.setRefreshing(false) }
        articalsViewModel= ArticalsViewModel(this,this)
        loading()
        articalsViewModel.getCategories(catId)
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