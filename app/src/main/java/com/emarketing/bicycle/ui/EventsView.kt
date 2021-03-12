package com.emarketing.bicycle.ui

import com.emarketing.bicycle.data.Event
import java.util.ArrayList

interface EventsView {
    fun onSuccess(events: ArrayList<Event>)
    fun onFailer(message: String)
}