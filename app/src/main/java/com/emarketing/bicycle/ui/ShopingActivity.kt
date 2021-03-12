package com.emarketing.bicycle.ui

import android.os.Bundle
import com.emarketing.bicycle.R
import com.emarketing.bicycle.data.Material
import com.emarketing.bicycle.mvvm.BaseActivity
import com.emarketing.bicycle.vm.MaterialAdapter
import com.emarketing.bicycle.vm.ShopingViewModel
import kotlinx.android.synthetic.main.activity_shoping.*

class ShopingActivity : BaseActivity(),ShopingView {
    var catId=1
    lateinit var shopingViewModel: ShopingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shoping)
        getMore.setOnRefreshListener { getMore.setRefreshing(false) }
        shopingViewModel= ShopingViewModel(this,this)
        loading()
        shopingViewModel.getMaterialList()
    }

    override fun onFailer(message: String) {
        stopLoading()
        showMessage(message )
    }

    override fun onSuccess(materials: ArrayList<Material>) {
        stopLoading()
        content.adapter=MaterialAdapter(this,materials)
    }

}