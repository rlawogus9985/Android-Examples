package com.example.globalaos

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import com.example.globalaos.databinding.PopOverBinding

class PopOver(context: Context,val detailView: View) : Dialog(context){

    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = PopOverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window!!.setDimAmount(0f)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        val density = context.resources.displayMetrics.density
//        val resourceId = context.resources.getIdentifier("status_bar_height","dimen","android")
//        val statusBarHeight = if (resourceId > 0) context.resources.getDimensionPixelSize(resourceId) else 0
//
//        window?.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
//
//
//        val layoutParams = WindowManager.LayoutParams()
//        layoutParams.copyFrom(window?.attributes)
//        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
//        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
//        layoutParams.gravity = Gravity.TOP or Gravity.END
//        layoutParams.x = (30 * density).toInt() // x좌표
//        Log.d("jh","${statusBarHeight}, ${detailView.height}")
//        layoutParams.y = statusBarHeight + detailView.height // y좌표
//        window?.attributes = layoutParams

        binding.blockButton.setOnClickListener{
            dismiss()
        }
        binding.reportButton.setOnClickListener {
            dismiss()
        }
    }

}
