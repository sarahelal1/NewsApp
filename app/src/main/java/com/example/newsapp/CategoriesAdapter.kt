package com.example.newsapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.model.Category
import com.google.android.material.card.MaterialCardView

class CategoriesAdapter(var items:List<Category>) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {
val RIGHT_SIDED_CODE=2
    val LEFT_SIDED_CODE=1



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view:View?
        if (viewType == RIGHT_SIDED_CODE) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.right_sided_category, parent, false)

        }else
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.left_sided_category, parent, false)

        return ViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return if(position%2==0) RIGHT_SIDED_CODE else LEFT_SIDED_CODE
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=items.get(position)
        holder.categorytitle.text= item.title.toString()
        holder.categoryImage.setImageResource(item.imageId)
        holder.materialCard.setCardBackgroundColor(ContextCompat
            .getColor(holder.itemView.context,item.backgroundColor))
        holder.itemView.setOnClickListener{
            onItemClicked?.onCategoryClicked(position,item)
        }
    }
    var onItemClicked:OnItemClicked?=null
    interface OnItemClicked{
        fun onCategoryClicked(pos:Int,item:Category)
    }

    override fun getItemCount(): Int {
       return items.size
    }
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val materialCard=itemView.findViewById<MaterialCardView>(R.id.parent)
        val categorytitle=itemView.findViewById<TextView>(R.id.category_title)
        val categoryImage=itemView.findViewById<ImageView>(R.id.category_image)

    }
}