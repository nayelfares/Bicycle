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
import com.emarketing.bicycle.data.Article
import com.emarketing.bicycle.ui.ArticleDetails
import kotlinx.android.synthetic.main.category_item_row.view.*


class ArticleAdapter(val context:Context, val articles:ArrayList<Article>) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=ViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.category_item_row, parent, false))
        return view
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]
        if (article.title!=null)
             holder.title.text=article.title
        if (article.body!=null)
            holder.body.text=article.body
        if (article.photos!=null&&article.photos.isNotEmpty())
            Glide.with(context)
                .load(article.photos[0].toUrl())
                .into(holder.photo)
        if (position%2==0)
            holder.itemView.setBackgroundColor(context.getColor(R.color.colorAccentTransparence))
        else
            holder.itemView.setBackgroundColor(context.getColor(R.color.white))
        holder.itemView.setOnClickListener {
            val intent= Intent(context,ArticleDetails::class.java)
            intent.putExtra("article",article)
            context.startActivity(intent)
        }
    }

    // total number of rows
    override fun getItemCount(): Int {
        return articles.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        var photo=itemView.icon
        val title=itemView.title
        val body =itemView.body

    }
}