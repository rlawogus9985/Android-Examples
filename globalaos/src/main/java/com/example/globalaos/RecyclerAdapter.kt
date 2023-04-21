package com.example.globalaos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class ImageSliderAdapter(val context: Context, val sliderImage: ArrayList<String>) :
    RecyclerView.Adapter<ImageSliderAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_slider, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindSliderImage(sliderImage[position % sliderImage.size])
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mImageView = itemView.findViewById<ImageView>(R.id.imageSlider)

        fun bindSliderImage(imageURL: String?) {
            Glide.with(context)
                .load(imageURL)
                .into(mImageView)
        }
    }
}