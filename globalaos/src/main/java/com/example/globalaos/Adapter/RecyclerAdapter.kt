package com.example.globalaos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.globalaos.databinding.ItemSliderBinding


class ImageSliderAdapter(val sliderImage: ArrayList<String>) :
    RecyclerView.Adapter<ImageSliderAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindSliderImage(sliderImage[position % sliderImage.size])
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    inner class MyViewHolder(val binding: ItemSliderBinding) : RecyclerView.ViewHolder(binding.root) {
        private val mImageView = binding.imageSlider

        fun bindSliderImage(imageURL: String?) {
            Glide.with(mImageView)
                .load(imageURL)
                .into(mImageView)
        }
    }
}