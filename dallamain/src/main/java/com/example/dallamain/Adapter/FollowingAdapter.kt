package com.example.dallamain.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dallamain.Data.FollowingData
import com.example.dallamain.R
import com.example.dallamain.databinding.ItemFollowingBinding
import com.example.dallamain.databinding.ItemFollowingEtcBinding

class FollowingAdapter(private val item: ArrayList<FollowingData>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_FOLLOWING = 0
    private val VIEW_TYPE_ETC = 1
    private val ETC_POSITION = 5

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            VIEW_TYPE_FOLLOWING -> {
                val binding = ItemFollowingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                FollowingViewHolder(binding)
            }
            else -> {
                val binding = ItemFollowingEtcBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                EtcViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int {
        return ETC_POSITION + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == ETC_POSITION){
            VIEW_TYPE_ETC
        } else {
            VIEW_TYPE_FOLLOWING
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = item[position]

        if(getItemViewType(position) == VIEW_TYPE_FOLLOWING){
            val holder = holder as FollowingViewHolder
            if (position < ETC_POSITION){
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
            }

            if (position == 0){
                val layoutParams = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.setMargins(dpToPx(16, holder.itemView.context),0,dpToPx(10, holder.itemView.context),0)
                holder.itemView.layoutParams = layoutParams
            } else {
                val layoutParams = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.setMargins(0,0,dpToPx(10, holder.itemView.context),0)
                holder.itemView.layoutParams = layoutParams
            }
//            else if (position == ETC_POSITION - 1){
//                val layoutParams = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
//                layoutParams.setMargins(0,0,dpToPx(4, holder.itemView.context),0)
//                holder.itemView.layoutParams = layoutParams
//            }
        } else if(getItemViewType(position) == VIEW_TYPE_ETC){
            val holder = holder as EtcViewHolder
            holder.etcText.text = "+${item.size - ETC_POSITION}"
            val layoutParams = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.setMargins(0,dpToPx(5, holder.itemView.context),dpToPx(4, holder.itemView.context),0)
            holder.itemView.layoutParams = layoutParams
        }

    }

    class FollowingViewHolder(private val binding: ItemFollowingBinding) : RecyclerView.ViewHolder(binding.root){
        val text = binding.followingText
        val image = binding.followingProfileImage
        val backgroundImage = binding.firstBackground
    }
    class EtcViewHolder(private val binding: ItemFollowingEtcBinding): RecyclerView.ViewHolder(binding.root){
        val etcText = binding.etcText
    }


    fun dpToPx(dp: Int, context: Context): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density).toInt()
    }
}