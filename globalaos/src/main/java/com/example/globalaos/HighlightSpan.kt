package com.example.globalaos

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.text.style.LineBackgroundSpan

class HighlightSpan(private val customBackgroundColor: Int) : LineBackgroundSpan {
    override fun drawBackground(
        canvas: Canvas,
        paint: Paint,
        left: Int,
        right: Int,
        top: Int,
        baseline: Int,
        bottom: Int,
        text: CharSequence,
        start: Int,
        end: Int,
        lineNumber: Int
    ) {
        val originalPaint = paint.color
        val halfHeight = (bottom - top) / 2
        val rect = Rect(left, bottom - halfHeight, right, bottom)
        paint.color = customBackgroundColor
        canvas.drawRect(rect, paint)

        // 페인트의 색을 본래 색으로 안바꿔주면 다른 text의 색에 전부 영향이 간다.
        paint.color = originalPaint

    }

}
