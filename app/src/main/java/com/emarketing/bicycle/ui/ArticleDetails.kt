package com.emarketing.bicycle.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.emarketing.bicycle.R
import com.emarketing.bicycle.data.Article
import com.emarketing.bicycle.mvvm.BaseActivity
import com.emarketing.bicycle.vm.PhotoAdapter
import kotlinx.android.synthetic.main.activity_article_details.*

class ArticleDetails : BaseActivity() {
    lateinit var currentLocation:Article
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_details)
        currentLocation=getIntent().getParcelableExtra<Article>("article")!!
        name.text=currentLocation.title
        description.text=currentLocation.body
        if (currentLocation.photos==null ||currentLocation.photos.isEmpty())
            photosView.visibility= View.GONE
        photosView.adapter=PhotoAdapter(this,currentLocation.photos)
    }
}