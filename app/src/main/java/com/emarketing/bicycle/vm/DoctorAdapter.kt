package com.emarketing.bicycle.vm

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.emarketing.bicycle.R
import com.emarketing.bicycle.api.toUrl
import com.emarketing.bicycle.data.Doctor
import com.emarketing.bicycle.ui.DoctorDetails
import kotlinx.android.synthetic.main.doctor_item_row.view.*
import java.lang.Exception


class DoctorAdapter(val context:Context, val articles:ArrayList<Doctor>) : RecyclerView.Adapter<DoctorAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=ViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.doctor_item_row, parent, false))
        return view
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val doctor = articles[position]
        holder.name.text=doctor.name
        holder.specialization.text=doctor.specialization
        try {
            holder.rating.rating = doctor.rating!!.toInt().toFloat()
        }catch (e:Exception){
            holder.rating.rating = 1f
        }
        Glide.with(context)
                .load(doctor.photo.toUrl())
                .into(holder.photo)
        if (position%2==0)
            holder.itemView.setBackgroundColor(context.getColor(R.color.colorAccentTransparence))
        else
            holder.itemView.setBackgroundColor(context.getColor(R.color.white))
        holder.itemView.setOnClickListener {
            val intent= Intent(context,DoctorDetails::class.java)
            intent.putExtra("doctor",doctor)
            context.startActivity(intent)
        }
    }

    // total number of rows
    override fun getItemCount(): Int {
        return articles.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        var photo=itemView.image
        val name=itemView.name
        val specialization =itemView.specialization
        val rating=itemView.rating
    }
}