package com.emarketing.bicycle.vm

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.emarketing.bicycle.R
import com.emarketing.bicycle.api.toUrl
import kotlinx.android.synthetic.main.photo_item.view.*


class PhotoAdapter(val context:Context, val photos:ArrayList<String>) : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=ViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.photo_item, parent, false))
        return view
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
            .load(photos[position].toUrl())
            .into(holder.image)
    }

    // total number of rows
    override fun getItemCount(): Int {
        return photos.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        var image=itemView.image

    }
}