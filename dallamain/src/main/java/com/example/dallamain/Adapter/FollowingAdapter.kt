package com.example.dallamain.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dallamain.Data.FollowingData
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
            .load(currentItem.imageUrl)
            .into(holder.image)
    }

    class FollowingViewHolder(private val binding: ItemFollowingBinding) : RecyclerView.ViewHolder(binding.root){
        val text = binding.followingText
        val image = binding.followingProfileImage
    }
}