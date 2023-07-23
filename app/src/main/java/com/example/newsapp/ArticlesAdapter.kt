package com.example.newsapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.api.ApiManager
import com.example.newsapp.model.ArticlesItem

class ArticlesAdapter(var items:List<ArticlesItem?>?=null) :
    RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {
    var onItemClickListener : OnItemClickListener?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
     val view=LayoutInflater.from(parent.context).inflate(R.layout.item_article,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article=items!!.get(position)?: return
        holder.authorTextView.text=article.author.toString()
        holder.titleTextView.text=article.title
        holder.dateTextView.text=article.publishedAt
        Glide.with(holder.itemView)
            .load(article?.urlToImage)
            .into(holder.image)
        holder.itemView.setOnClickListener{

            onItemClickListener?.onItemClickListener(article)
        }
    }
    interface OnItemClickListener{
        fun onItemClickListener(article: ArticlesItem)
    }
    override fun getItemCount(): Int {
return if (items==null) 0 else items!!.size
    }
    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val image:ImageView=itemView.findViewById(R.id.item_article_image)
        val authorTextView:TextView=itemView.findViewById(R.id.item_article_author)
        val titleTextView:TextView=itemView.findViewById(R.id.item_article_title)
        val dateTextView:TextView=itemView.findViewById(R.id.item_article_date)
    }


}