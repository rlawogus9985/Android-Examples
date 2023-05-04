package com.example.dallamain.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dallamain.Data.LiveSectionData
import com.example.dallamain.R
import com.example.dallamain.databinding.ItemLivesectionBinding

class LiveSectionAdapter(val item: ArrayList<LiveSectionData>) : RecyclerView.Adapter<LiveSectionAdapter.CustomViewHolder>(){

    class CustomViewHolder(val binding: ItemLivesectionBinding) : RecyclerView.ViewHolder(binding.root){
        val profileImage = binding.profileImage
        val bdgImage1 = binding.bdgImage1
        val bdgImage2 = binding.bdgImage2
        val liveTitleText = binding.liveTitleText
        val medalImage = binding.medalImage
        val genderImage = binding.genderImage
        val djName = binding.DJNameText
        val peopleCountImage = binding.peopleGSImage
        val peopleCount = binding.peopleCountText
        val likeImage = binding.likeImage
        val likeCount = binding.likeCountText
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentItem = item[position]
        // 프로필 이미지
        holder.profileImage.clipToOutline = true
        Glide.with(holder.itemView.context)
            .load(currentItem.profileImage)
            .into(holder.profileImage)
        // 배지1
        if (currentItem.bdgImage1 == null){
            val params = holder.liveTitleText.layoutParams as ConstraintLayout.LayoutParams
            params.topToTop = holder.profileImage.id
            params.topMargin = dpToPx(9, holder.itemView.context.applicationContext)
        } else {
            holder.bdgImage1.setImageResource(currentItem.bdgImage1)
        }
        // 배지2
        if (currentItem.bdgImage2 != null){
            holder.bdgImage2.setImageResource(currentItem.bdgImage2)
        }
        // 방송 제목
        holder.liveTitleText.text = currentItem.liveTitleText
        // 메달
        if (currentItem.medalImage == null){
            val params = holder.genderImage.layoutParams as ConstraintLayout.LayoutParams
            params.startToEnd = holder.profileImage.id
            params.marginStart = dpToPx(12,holder.itemView.context.applicationContext)
        } else {
            Glide.with(holder.itemView.context).load(currentItem.medalImage).into(holder.medalImage)
        }
        // 성별
        if (currentItem.genderImage == "m"){
            Glide.with(holder.itemView.context).load(R.drawable.ico_male).into(holder.genderImage)
        } else {
            Glide.with(holder.itemView.context).load(R.drawable.ico_female).into(holder.genderImage)
        }

        // DJ이름
        holder.djName.text = currentItem.djName
        // 사람수 이미지
        Glide.with(holder.itemView.context).load(R.drawable.people_g_s).into(holder.peopleCountImage)
        // 사람수 gson 자동 String변환
        holder.peopleCount.text = currentItem.peopleCount
        // 좋아요 수 이미지
        if (currentItem.risingYn == "y"){
            Glide.with(holder.itemView.context).load(R.drawable.ico_booster_2).into(holder.likeImage)
            holder.likeCount.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.pink))
        } else {
            Glide.with(holder.itemView.context).load(R.drawable.heart).into(holder.likeImage)
            holder.likeCount.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.gray_99))
        }
        // 좋아요 수
        holder.likeCount.text = currentItem.likeCount

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = ItemLivesectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return item.size
    }
    fun dpToPx(dp: Int, context: Context): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density).toInt()
    }
}