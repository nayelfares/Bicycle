package com.emarketing.bicycle.ui

import android.app.ActionBar
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.Window
import com.emarketing.bicycle.R
import com.emarketing.bicycle.data.Event
import com.emarketing.bicycle.data.Material
import com.emarketing.bicycle.mvvm.BaseActivity
import com.emarketing.bicycle.vm.CreateEventViewModel
import kotlinx.android.synthetic.main.activity_create_event.*
import kotlinx.android.synthetic.main.pick_date.*
import kotlinx.android.synthetic.main.pick_time.*

class CreateEvent : BaseActivity() ,CreateEventView{
    lateinit var createEventViewModel: CreateEventViewModel
    var currentEvent:Event?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_event)
        currentEvent =intent.getParcelableExtra<Event>("event")
        createEventViewModel= CreateEventViewModel(this,this)
        events()
    }

    fun events(){

        if (currentEvent!=null){
            eventName.setText(currentEvent!!.name)
            participants.setText(currentEvent!!.number_members.toString())
            startDate.setText(currentEvent!!.start_date.substring(0,10))
            startTime.setText(currentEvent!!.start_date.substring(11))
            endDate.setText(currentEvent!!.end_date.substring(0,10))
            endTime.setText(currentEvent!!.end_date.substring(11))
            description.setText(currentEvent!!.description)
            objectives.setText(currentEvent!!.objectives)
        }
        startDate.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    val dialog =
                        Dialog(this, R.style.Theme_Design_BottomSheetDialog)
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    dialog.setCancelable(false)
                    dialog.setContentView(R.layout.pick_date)
                    dialog.cancelOptions.setOnClickListener {
                        dialog.dismiss()
                    }
                    dialog.submit.setOnClickListener {
                        var month=(dialog.datePicker.month+1).toString()
                        if (month.length==1)
                            month="0$month"
                        var days=dialog.datePicker.dayOfMonth.toString()
                        if (days.length==1)
                            days="0$days"
                        val pirthdateString=dialog.datePicker.year.toString()+"-$month-$days"
                        startDate.setText(pirthdateString)
                        dialog.dismiss()
                    }

                    val window: Window = dialog.window!!
                    window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT)
                    dialog.show()
                }
            }

            v?.onTouchEvent(event) ?: true
        }
        startTime.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    val dialog =
                        Dialog(this, R.style.Theme_Design_BottomSheetDialog)
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    dialog.setCancelable(false)
                    dialog.setContentView(R.layout.pick_time)
                    dialog.cancelOptions1.setOnClickListener {
                        dialog.dismiss()
                    }
                    dialog.timePicker.setIs24HourView(true)
                    dialog.submit1.setOnClickListener {
                        var hours=dialog.timePicker.hour.toString()
                        if (hours.length==1)
                            hours="0$hours"
                        var minutes=dialog.timePicker.minute.toString()
                        if (minutes.length==1)
                            minutes="0$minutes"
                        val startTimeString="$hours:$minutes:00"
                        startTime.setText(startTimeString)
                        dialog.dismiss()
                    }

                    val window: Window = dialog.window!!
                    window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT)
                    dialog.show()
                }
            }

            v?.onTouchEvent(event) ?: true
        }
        endDate.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    val dialog =
                        Dialog(this, R.style.Theme_Design_BottomSheetDialog)
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    dialog.setCancelable(false)
                    dialog.setContentView(R.layout.pick_date)
                    dialog.cancelOptions.setOnClickListener {
                        dialog.dismiss()
                    }
                    dialog.submit.setOnClickListener {
                        var month=(dialog.datePicker.month+1).toString()
                        if (month.length==1)
                            month="0$month"
                        var days=dialog.datePicker.dayOfMonth.toString()
                        if (days.length==1)
                            days="0$days"
                        val pirthdateString=dialog.datePicker.year.toString()+"-$month-$days"
                        endDate.setText(pirthdateString)
                        dialog.dismiss()
                    }

                    val window: Window = dialog.window!!
                    window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT)
                    dialog.show()
                }
            }

            v?.onTouchEvent(event) ?: true
        }
        endTime.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    val dialog =
                        Dialog(this, R.style.Theme_Design_BottomSheetDialog)
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    dialog.setCancelable(false)
                    dialog.setContentView(R.layout.pick_time)
                    dialog.cancelOptions1.setOnClickListener {
                        dialog.dismiss()
                    }
                    dialog.timePicker.setIs24HourView(true)
                    dialog.submit1.setOnClickListener {
                        var hours=dialog.timePicker.hour.toString()
                        if (hours.length==1)
                            hours="0$hours"
                        var minutes=dialog.timePicker.minute.toString()
                        if (minutes.length==1)
                            minutes="0$minutes"
                        val startTimeString="$hours:$minutes:00"
                        endTime.setText(startTimeString)
                        dialog.dismiss()
                    }

                    val window: Window = dialog.window!!
                    window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT)
                    dialog.show()
                }
            }

            v?.onTouchEvent(event) ?: true
        }
        create.setOnClickListener {
            loading()
            if (currentEvent==null)
                createEventViewModel.addEvent(
                    eventName.text.toString(),
                    startDate.text.toString()+" "+startTime.text.toString(),
                    endDate.text.toString()+" "+endTime.text.toString(),
                    description.text.toString(),
                    objectives.text.toString(),
                    participants.text.toString().toInt(),
                )
            else
                createEventViewModel.updateEvent(
                    currentEvent!!.id.toString(),
                    eventName.text.toString(),
                    startDate.text.toString()+" "+startTime.text.toString(),
                    endDate.text.toString()+" "+endTime.text.toString(),
                    description.text.toString(),
                    objectives.text.toString(),
                    participants.text.toString().toInt(),
                )
        }
    }

    override fun onSuccess(message: String) {
        stopLoading()
        showMessage(message)
        val resultIntent = Intent()
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    override fun onFailer(message: String) {
        stopLoading()
        showMessage(message)
    }
}