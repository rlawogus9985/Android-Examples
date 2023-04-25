package com.example.globalaos

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.globalaos.databinding.ListInformationBinding
import kotlin.random.Random

class InformationAdapter(val informationList:ArrayList<Informations>) : RecyclerView.Adapter<InformationAdapter.CustomViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = ListInformationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return informationList.size
    }


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        holder.painterImageView.layoutParams.width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            Random.nextInt(80, 160).toFloat(),
            holder.painterImageView.context.resources.displayMetrics).toInt()

        holder.painterImageView2.layoutParams.width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            Random.nextInt(80, 160).toFloat(),
            holder.painterImageView.context.resources.displayMetrics).toInt()

        holder.painterImageView.setImageResource(informationList.get(position).painterImageNumber)

        informationList.get(position).painterImageNumber2?.let {
            holder.painterImageView2.setImageResource(
                it
            )
        }

        val spannable = SpannableString(informationList.get(position).question)
        Log.d("absd","${informationList.get(position).question}")

        val backgroundColorSpan = BackgroundColorSpan(Color.RED)
        spannable.setSpan(backgroundColorSpan,0,1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        holder.question.text = spannable

//        holder.question.text = informationList.get(position).question

        holder.answer.text = informationList.get(position).answer

        if (position > informationList.size-4){
            holder.painterImageView2.visibility = View.VISIBLE
        }
        holder.lockedButton.setOnClickListener{
            if(holder.painterImageView.layoutParams.width > 290){
                holder.painterImageView.layoutParams.width = 290
            }
            holder.lockedButton.visibility = View.GONE
            holder.unlockButton.visibility = View.VISIBLE
        }
        holder.unlockButton.setOnClickListener{
            holder.unlockButton.visibility = View.GONE
            holder.painterImageView.visibility = View.GONE
            holder.painterImageView2.visibility = View.GONE
            holder.answer.visibility = View.VISIBLE

        }
    }

    class CustomViewHolder(private val binding: ListInformationBinding) : RecyclerView.ViewHolder(binding.root){
        val painterImageView = binding.painterImageView
        val question = binding.questionInformation
        val answer = binding.answerInformation
        val lockedButton = binding.iconLocked
        val unlockButton = binding.iconUnlock
        val painterImageView2 = binding.painterImageView2
    }
}