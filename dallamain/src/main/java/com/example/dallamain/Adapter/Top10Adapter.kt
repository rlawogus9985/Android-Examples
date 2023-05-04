package com.example.dallamain.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dallamain.Data.Top10Data
import com.example.dallamain.databinding.ItemTop10Binding

class Top10Adapter(val item: ArrayList<Top10Data>) : RecyclerView.Adapter<Top10Adapter.CustomViewHolder>(){

    class CustomViewHolder(val binding: ItemTop10Binding): RecyclerView.ViewHolder(binding.root){
        val image = binding.top10ProfileImage
        val name = binding.top10Text
        val number = binding.top10Number
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentItem = item[position]
        holder.name.text = currentItem.name
        holder.image.clipToOutline = true
        Glide.with(holder.itemView.context)
            .load(currentItem.imageUrl)
            .into(holder.image)
        Glide.with(holder.itemView.context)
            .load(currentItem.number)
            .into(holder.number)

        if (position == 0){
            val layoutParams = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.setMargins(dpToPx(16, holder.itemView.context),0,dpToPx(8, holder.itemView.context),0)
            holder.itemView.layoutParams = layoutParams
        } else if (position == item.size - 1){
            val layoutParams = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.setMargins(0,0,dpToPx(16, holder.itemView.context),0)
            holder.itemView.layoutParams = layoutParams
        } else {
            val layoutParams = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.setMargins(0,0,dpToPx(8, holder.itemView.context),0)
            holder.itemView.layoutParams = layoutParams
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = ItemTop10Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return item.size
    }
    fun dpToPx(dp: Int, context: Context): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density).toInt()
    }
}