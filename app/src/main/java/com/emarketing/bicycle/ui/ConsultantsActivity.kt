package com.emarketing.bicycle.ui

import android.os.Bundle
import com.emarketing.bicycle.R
import com.emarketing.bicycle.data.Doctor
import com.emarketing.bicycle.mvvm.BaseActivity
import com.emarketing.bicycle.vm.ConsultantsViewModel
import com.emarketing.bicycle.vm.DoctorAdapter
import kotlinx.android.synthetic.main.activity_articals.*

class ConsultantsActivity : BaseActivity(),ConsultantsView {
    lateinit var consultantsViewModel: ConsultantsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articals)
        toolbar.text=intent.getStringExtra("catName")
        getMore.setOnRefreshListener { getMore.setRefreshing(false) }
        consultantsViewModel= ConsultantsViewModel(this,this)
        loading()
        consultantsViewModel.getDoctorsList()
    }

    override fun onFailer(message: String) {
        stopLoading()
        showMessage(message )
    }

    override fun onSuccess(doctors: ArrayList<Doctor>) {
        stopLoading()
        content.adapter= DoctorAdapter(this,doctors)
    }

}