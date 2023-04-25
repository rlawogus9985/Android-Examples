package com.example.globalaos

import android.graphics.Canvas
import android.graphics.Paint
import android.text.style.ReplacementSpan

class HighlightSpan(private val backgroundColor: Int, private val underlineColor: Int) : ReplacementSpan() {
    override fun getSize(paint: Paint, text: CharSequence?, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
        return paint.measureText(text, start, end).toInt()
    }

    override fun draw(canvas: Canvas, text: CharSequence?, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
        // 밑줄 높이 계산
        val underlineHeight = paint.textSize / 10

        // 형광펜 배경 그리기
        val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = backgroundColor
            style = Paint.Style.FILL
        }
        canvas.drawRect(x, y - paint.ascent(), x + paint.measureText(text, start, end), y + underlineHeight, backgroundPaint)

        // 밑줄 그리기
        val underlinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = underlineColor
            style = Paint.Style.FILL
        }
        canvas.drawRect(x, y + underlineHeight, x + paint.measureText(text, start, end), y + underlineHeight * 2, underlinePaint)
    }
}
