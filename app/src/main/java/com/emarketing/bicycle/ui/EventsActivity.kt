package com.emarketing.bicycle.ui

import android.app.Activity
import android.content.Intent
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
        add.setOnClickListener {
            startActivityForResult(Intent(this,CreateEvent::class.java),1002)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==1002 && resultCode== Activity.RESULT_OK){
            loading()
            eventsViewModel.getEventList(userId)
        }
    }
}