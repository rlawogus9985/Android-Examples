package com.example.dallamain.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dallamain.Data.EventBannerData
import com.example.dallamain.databinding.ItemBannerBinding

class BannerAdapter(val item: ArrayList<EventBannerData>) : RecyclerView.Adapter<BannerAdapter.CustomViewHolder>() {
    class CustomViewHolder(val binding: ItemBannerBinding): RecyclerView.ViewHolder(binding.root){
        val banner = binding.bannerImage
        fun bindSliderImage(imageUrl: String?) {
            Glide.with(banner)
                .load(imageUrl)
                .into(banner)
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentItem = item[position % item.size]
        holder.bindSliderImage(currentItem.event_bannerUrl)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = ItemBannerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }
}