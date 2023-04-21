package com.example.globalaos

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class InformationAdapter(val informationList:ArrayList<Informations>) : RecyclerView.Adapter<InformationAdapter.CustomViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_information,parent,false)
        return CustomViewHolder(view)
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

        holder.question.text = informationList.get(position).question
        holder.answer.text = informationList.get(position).answer
        if (position > 13){
            holder.painterImageView2.visibility = View.VISIBLE
        }
        holder.lockedButton.setOnClickListener{
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

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val painterImageView = itemView.findViewById<ImageView>(R.id.painterImageView)
        val painterImageView2 = itemView.findViewById<ImageView>(R.id.painterImageView2)
        val question = itemView.findViewById<TextView>(R.id.questionInformation)
        val answer = itemView.findViewById<TextView>(R.id.answerInformation)
        val lockedButton = itemView.findViewById<ImageView>(R.id.iconLocked)
        val unlockButton = itemView.findViewById<TextView>(R.id.iconUnlock)
    }
}