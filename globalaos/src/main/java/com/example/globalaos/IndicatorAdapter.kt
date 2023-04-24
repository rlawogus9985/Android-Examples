package com.example.globalaos

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class IndicatorAdapter(private val itemCount: Int) :
    RecyclerView.Adapter<IndicatorAdapter.IndicatorViewHolder>() {

    private var currentPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndicatorViewHolder {
        val view: View = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.indicator, parent, false)
        return IndicatorViewHolder(view)
    }

    override fun onBindViewHolder(holder: IndicatorViewHolder, position: Int) {
        holder.bind(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setCurrentPosition(position: Int) {
        currentPosition = position
        notifyDataSetChanged()
    }

    inner class IndicatorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val indicatorView: View = itemView.findViewById(R.id.indicatorImageView)

        fun bind(position: Int) {
            if (position == currentPosition) {
                indicatorView.setBackgroundResource(R.drawable.selected_dot)
            } else {
                indicatorView.setBackgroundResource(R.drawable.default_dot)
            }
        }
    }

    override fun getItemCount(): Int {
        return itemCount
    }

}