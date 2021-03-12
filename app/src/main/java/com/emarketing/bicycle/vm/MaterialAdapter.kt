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
import com.emarketing.bicycle.data.Material
import com.emarketing.bicycle.ui.MaterialDetails
import kotlinx.android.synthetic.main.category_item_row.view.*


class MaterialAdapter(val context:Context, val materials:ArrayList<Material>) : RecyclerView.Adapter<MaterialAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=ViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.category_item_row, parent, false))
        return view
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val material = materials[position]
        if (material.name!=null)
             holder.title.text=material.name
        if (material.description!=null)
            holder.body.text=material.description
        if (material.photo!=null&&material.photo.isNotEmpty())
            Glide.with(context)
                .load(material.photo.toUrl())
                .into(holder.photo)
        if (position%2==0)
            holder.itemView.setBackgroundColor(context.getColor(R.color.colorAccentTransparence))
        else
            holder.itemView.setBackgroundColor(context.getColor(R.color.white))
        holder.itemView.setOnClickListener {
            val intent= Intent(context,MaterialDetails::class.java)
            intent.putExtra("material",material)
            context.startActivity(intent)
        }
    }

    // total number of rows
    override fun getItemCount(): Int {
        return materials.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        var photo=itemView.icon
        val title=itemView.title
        val body =itemView.body

    }
}