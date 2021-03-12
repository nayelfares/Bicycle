package com.emarketing.bicycle.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.RatingBar
import com.bumptech.glide.Glide
import com.emarketing.bicycle.R
import com.emarketing.bicycle.api.MainAPIManager
import com.emarketing.bicycle.api.toUrl
import com.emarketing.bicycle.data.*
import com.emarketing.bicycle.mvvm.BaseActivity
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_doctor_details.*

class DoctorDetails : BaseActivity() {
    lateinit var doctor:Doctor
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_details)
        doctor=getIntent().getParcelableExtra<Doctor>("doctor")!!
        Glide.with(this)
            .load(doctor.photo.toUrl())
            .into(photo)
        username.text=doctor.name
        specialization.text=doctor.specialization
        address.text=doctor.address
        email.text=doctor.email
        phone.text=doctor.mobile
        whatsapp.text=doctor.whatsapp

        rating.onRatingBarChangeListener=object :RatingBar.OnRatingBarChangeListener{
            override fun onRatingChanged(ratingBar: RatingBar?, rating: Float, fromUser: Boolean) {
                submit.visibility=View.VISIBLE
            }

        }

        submit.setOnClickListener {
            loading()
            val apiManager= MainAPIManager().provideRetrofitInterface().create(RequestInterface::class.java)
            val rateVar  = apiManager.rate(token,doctor.id,rating.rating.toInt(),id)
            rateVar.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Response> {
                    override fun onComplete() { }
                    override fun onSubscribe(d: Disposable) { }
                    override fun onNext(t: Response) {
                        if (t.success!=false) {
                            showMessage(t.message)
                            stopLoading()
                            submit.visibility = View.GONE
                            rating.setIsIndicator(true)
                        }
                        else {
                            showMessage(t.message)
                            stopLoading()
                        }
                    }
                    override fun onError(e: Throwable) {
                        showMessage(resources.getString(R.string.check_intenet_connection))
                        stopLoading()
                    }
                })
        }
    }
}