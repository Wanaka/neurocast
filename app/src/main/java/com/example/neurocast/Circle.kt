package com.example.neurocast

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat


class Circle(context: Context, attrs: AttributeSet) : View(context, attrs) {
    lateinit var rectF: RectF
    lateinit var circle: Paint
    lateinit var can: Canvas
    var startAngle: Float = 160.0f
    var sweepAngleToday: Float = 225.0f
    var sweepAngleYesterday: Float = 225.0f


    override fun onDraw(canvas: Canvas) {
        can = canvas
        super.onDraw(can)

        circleShadow(20F, 20F, 800F, 800F)
        circleBlue()
        circleWhite(70F, 70F, 750F, 750F)
        circleOrange()
        circleWhite(100F, 100F, 720F, 720F)
    }

    private fun circleBlue() {
        circle = Paint()
        rectF = RectF(20F, 20F, 800F, 800F)
        circle.color = Color.BLUE

        can.drawArc(rectF, startAngle, sweepAngleToday, true, circle)
    }

    private fun circleOrange() {
        circle = Paint()
        rectF = RectF(70F, 70F, 750F, 750F)
        circle.color = ContextCompat.getColor(
            context,
            R.color.colorOrange
        )

        can.drawArc(rectF, startAngle, sweepAngleYesterday, true, circle)
    }

    private fun circleWhite(left: Float, top: Float, right: Float, bottom: Float) {
        circle = Paint()
        rectF = RectF(left, top, right, bottom)
        circle.color = Color.WHITE

        can.drawArc(rectF, startAngle, 360F, true, circle)
    }

    private fun circleShadow(left: Float, top: Float, right: Float, bottom: Float) {
        circle = Paint()
        rectF = RectF(left, top, right, bottom)
        circle.color = Color.WHITE

        circle.setShadowLayer(
            15.0F, 0.0F, 0.0F, ContextCompat.getColor(
                context,
                R.color.colorGray
            )
        )
        can.drawArc(rectF, startAngle, 220F, true, circle)
    }

    fun setData(today: HashMap<String, String>, yesterday: HashMap<String, String>) {
        sweepAngleToday = if (today["index"]!!.toFloat() < 0) 0.0F
        else today["index"]!!.toFloat() * 100.0F

        sweepAngleYesterday = if (yesterday["index"]!!.toFloat() < 0) 0.0F
        else yesterday["index"]!!.toFloat() * 100.0F

        invalidate()
    }

    fun animateArc(
        toAngle: Float,
        name: String
    ) {
        var angle: Float

        if (toAngle > 0) {
            angle = toAngle

            invalidate()

            val anim = ObjectAnimator.ofFloat(this, name, 0.0F, angle * 100.0F)
            anim.duration = 450
            anim.addUpdateListener {
                invalidate()
            }

            anim.start()
        }
    }
}