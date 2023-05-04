package com.example.dallamain.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dallamain.Data.TopBnrData
import com.example.dallamain.R
import com.example.dallamain.databinding.ItemTopbnrBinding

class TobBnrAdapter(private val item: ArrayList<TopBnrData>) : RecyclerView.Adapter<TobBnrAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = ItemTopbnrBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentItem = item[position % item.size]
        holder.bindSliderImage(currentItem.image_profile)
        holder.title.text = currentItem.title
        holder.djname.text = currentItem.mem_nickname
        if(currentItem.isBdg == 1){
            Glide.with(holder.itemView.context)
                .load(R.drawable.bdg_star)
                .into(holder.bdg)
        }
    }

    class CustomViewHolder(private val binding: ItemTopbnrBinding) : RecyclerView.ViewHolder(binding.root){
        val bnrImage = binding.topbnrImage

        fun bindSliderImage(imageURL: String?) {
            Glide.with(bnrImage)
                .load(imageURL)
                .into(bnrImage)
        }
        val title = binding.topbnrTitle
        val djname = binding.topbnrDjName
        val bdg = binding.topbnrBdg
    }
}