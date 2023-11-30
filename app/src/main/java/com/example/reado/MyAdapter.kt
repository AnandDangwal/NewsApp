package com.example.reado

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.reado.databinding.ItemNewsBinding


class MyAdapter(private val context: Context, private val listener: NewsItemClicked): RecyclerView.Adapter<MyViewHolder>() {

    private val items : ArrayList<NewsApiAttributes> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        val viewHolder = MyViewHolder(view)
        view.setOnClickListener(){
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = items[position]
        holder.newsTitle.text = currentItem.title
        holder.newsAbstract.text = currentItem.abstract
        holder.newsAuthorName.text = currentItem.author
        Glide.with(context).load(currentItem.imageUrl).into(holder.newsImage)
    }

    fun updateNewsItem(updateNewsItem : ArrayList<NewsApiAttributes>){
        items.clear()
        items.addAll(updateNewsItem)

        notifyDataSetChanged()
    }

}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    private val binding : ItemNewsBinding = ItemNewsBinding.bind(itemView)

//    val title : TextView = binding.tvTitle
    val newsImage : ImageView = binding.ivNewsImage
    val newsTitle : TextView = binding.tvTitle
    val newsAbstract : TextView = binding.tvAbstract
    val newsAuthorName : TextView = binding.tvAuthorName
}

interface  NewsItemClicked {

    fun onItemClicked(item: NewsApiAttributes)
}