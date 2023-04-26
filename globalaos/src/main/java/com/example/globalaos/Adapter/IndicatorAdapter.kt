package com.example.globalaos.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.globalaos.R
import com.example.globalaos.databinding.IndicatorBinding

class IndicatorAdapter(private val itemCount: Int) :
    RecyclerView.Adapter<IndicatorAdapter.IndicatorViewHolder>() {

    private var currentPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndicatorViewHolder {
        val binding = IndicatorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IndicatorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IndicatorViewHolder, position: Int) {
        holder.bind(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setCurrentPosition(position: Int) {
        currentPosition = position
        notifyDataSetChanged()
    }

    inner class IndicatorViewHolder(private val binding: IndicatorBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            if (position == currentPosition) {
                binding.indicatorImageView.setBackgroundResource(R.drawable.selected_dot)
            } else {
                binding.indicatorImageView.setBackgroundResource(R.drawable.default_dot)
            }
        }
    }

    override fun getItemCount(): Int {
        return itemCount
    }

}