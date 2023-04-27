package com.example.dallamain.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dallamain.databinding.ItemTopbnrBinding

class TobBnrAdapter(private val item: ArrayList<String>) : RecyclerView.Adapter<TobBnrAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = ItemTopbnrBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bindSliderImage(item[position % item.size])
    }

    class CustomViewHolder(private val binding: ItemTopbnrBinding) : RecyclerView.ViewHolder(binding.root){
        val bnrImage = binding.topbnrImage

        fun bindSliderImage(imageURL: String?) {
            Glide.with(bnrImage)
                .load(imageURL)
                .into(bnrImage)
        }
    }
}