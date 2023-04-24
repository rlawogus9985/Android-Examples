package com.example.globalaos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import com.example.globalaos.databinding.PopOverBinding

class PopOver2(context: Context, val detailView: View) {
    private val popupWindow: PopupWindow

    init {
        val inflater = LayoutInflater.from(context)
        val binding = PopOverBinding.inflate(inflater)
        popupWindow = PopupWindow(binding.root, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, false)

        binding.blockButton.setOnClickListener {
            popupWindow.dismiss()
        }

        binding.reportButton.setOnClickListener {
            popupWindow.dismiss()
        }

        val density = context.resources.displayMetrics.density
        val location = IntArray(2)
        detailView.getLocationOnScreen(location)

        val x = (location[0] + detailView.width - (30 * density)).toInt()
        val y = location[1] - getStatusBarHeight(context)

//        popupWindow.showAtLocation(detailView, Gravity.NO_GRAVITY, x, y)
        popupWindow.showAsDropDown(detailView,)
    }

    private fun getStatusBarHeight(context: Context): Int {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) context.resources.getDimensionPixelSize(resourceId) else 0
    }

}
