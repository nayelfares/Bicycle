package com.emarketing.bicycle.ui

import android.content.Intent
import android.os.Bundle
import com.emarketing.bicycle.R
import com.emarketing.bicycle.data.Material
import com.emarketing.bicycle.mvvm.BaseActivity
import com.emarketing.bicycle.vm.MaterialAdapter
import com.emarketing.bicycle.vm.ShopingViewModel
import kotlinx.android.synthetic.main.activity_shoping.*

class ShopingActivity : BaseActivity(),ShopingView {
    var user_id:String?=null
    lateinit var shopingViewModel: ShopingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shoping)
        user_id=intent.getStringExtra("user_id")
        getMore.setOnRefreshListener { getMore.setRefreshing(false) }
        shopingViewModel= ShopingViewModel(this,this)
        loading()
        shopingViewModel.getMaterialList(user_id)
        add.setOnClickListener {
            startActivity(Intent(this,AddMaterialActivity::class.java))
        }
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