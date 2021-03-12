package com.emarketing.bicycle.vm

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emarketing.bicycle.R
import com.emarketing.bicycle.data.Event
import kotlinx.android.synthetic.main.event_row.view.*


class EventAdapter(val context:Context, val materials:ArrayList<Event>) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=ViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.event_row, parent, false))
        return view
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = materials[position]
        with(holder){
            name.text=event.name
            description.text=event.description
            members.text=event.number_members
            startDate.text=event.start_date
            endDate.text =event.end_date
        }
    }

    // total number of rows
    override fun getItemCount(): Int {
        return materials.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        var name=itemView.name
        val description=itemView.description
        val members =itemView.members
        val startDate =itemView.startDate
        val endDate =itemView.endDate
    }
}