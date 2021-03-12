package com.emarketing.bicycle.ui

import android.os.Bundle
import com.emarketing.bicycle.R
import com.emarketing.bicycle.data.Event
import com.emarketing.bicycle.mvvm.BaseActivity
import com.emarketing.bicycle.vm.EventAdapter
import com.emarketing.bicycle.vm.EventsViewModel
import kotlinx.android.synthetic.main.activity_events.*
import org.jetbrains.anko.doAsync
import java.util.ArrayList

class EventsActivity : BaseActivity(),EventsView {
    lateinit var eventsViewModel: EventsViewModel
    var userId:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)
        userId=intent.getStringExtra("id")
        eventsViewModel= EventsViewModel(this,this)
        loading()
        doAsync {
            eventsViewModel.getEventList(userId)
        }
    }

    override fun onSuccess(events: ArrayList<Event>) {
        stopLoading()
        content.adapter = EventAdapter(this,events)
    }

    override fun onFailer(message: String) {
        stopLoading()
        showMessage(message)
    }
}