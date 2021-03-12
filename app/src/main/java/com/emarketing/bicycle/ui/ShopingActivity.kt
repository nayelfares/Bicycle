package com.emarketing.bicycle.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.emarketing.bicycle.R
import com.emarketing.bicycle.data.Material
import com.emarketing.bicycle.mvvm.BaseActivity
import com.emarketing.bicycle.vm.MaterialAdapter
import com.emarketing.bicycle.vm.ShopingViewModel
import kotlinx.android.synthetic.main.activity_shoping.*
import kotlinx.android.synthetic.main.activity_shoping.getMore

class ShopingActivity : BaseActivity(),ShopingView {
    var user_id:String?=null
    lateinit var shopingViewModel: ShopingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shoping)
        user_id=intent.getStringExtra("user_id")
        getMore.setOnRefreshListener {
            getMore.setRefreshing(false)
            loading()
            shopingViewModel.getMaterialList(user_id)
        }
        shopingViewModel= ShopingViewModel(this,this)
        loading()
        shopingViewModel.getMaterialList(user_id)
        add.setOnClickListener {
            startActivityForResult(Intent(this,AddMaterialActivity::class.java),1001)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==1001 && resultCode== Activity.RESULT_OK){
            loading()
            shopingViewModel.getMaterialList(user_id)
        }
    }

}