package com.example.dallamain.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dallamain.Data.FollowingData
import com.example.dallamain.R
import com.example.dallamain.databinding.ItemFollowingBinding

class FollowingAdapter(private val item: ArrayList<FollowingData>)
    : RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        val binding = ItemFollowingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        val currentItem = item[position]
        holder.text.text = currentItem.text
        holder.image.clipToOutline = true
        Glide.with(holder.itemView.context)
            .load(currentItem.profImg.url)
            .into(holder.image)
        if (currentItem.isBroadcasting == "y"){
            Glide.with(holder.itemView.context)
                .load(R.drawable.gradient_pink_circle_background)
                .into(holder.backgroundImage)
        } else if(currentItem.isBroadcasting == "n"){
            Glide.with(holder.itemView.context)
                .load(R.drawable.gradient_gray_circle_background)
                .into(holder.backgroundImage)
        }

        if (position == 0){
            val layoutParams = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.setMargins(dpToPx(16, holder.itemView.context),0,dpToPx(10, holder.itemView.context),0)
            holder.itemView.layoutParams = layoutParams
        } else if (position == item.size - 1){
            val layoutParams = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.setMargins(0,0,dpToPx(4, holder.itemView.context),0)
            holder.itemView.layoutParams = layoutParams
        } else {
            val layoutParams = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.setMargins(0,0,dpToPx(10, holder.itemView.context),0)
            holder.itemView.layoutParams = layoutParams
        }
    }

    class FollowingViewHolder(private val binding: ItemFollowingBinding) : RecyclerView.ViewHolder(binding.root){
        val text = binding.followingText
        val image = binding.followingProfileImage
        val backgroundImage = binding.firstBackground
    }

    fun dpToPx(dp: Int, context: Context): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density).toInt()
    }
}