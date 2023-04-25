package com.example.globalaos.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.globalaos.databinding.ItemPhotoBinding

class PhotoAdapter(val photoList: Array<Int>) : RecyclerView.Adapter<PhotoAdapter.CustomViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.photo.setImageResource(photoList.get(position))
    }

    class CustomViewHolder(private val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root){
        val photo = binding.photoImageView
    }

}