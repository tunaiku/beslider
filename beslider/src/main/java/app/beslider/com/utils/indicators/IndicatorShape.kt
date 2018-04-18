package app.beslider.com.utils.indicators

import android.content.Context
import android.view.Gravity
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import app.beslider.com.R

abstract class IndicatorShape(
        mContext: Context,
        mIndicator: Int,
        mustAnimateIndicator: Boolean
) : ImageView(mContext) {

    var isChecked = false
    var indicatorSize = mIndicator
    var mustAnimate = mustAnimateIndicator

    companion object {
        val ANIMATION_DURATION = 150
    }

    init {
        setup()
    }

    fun setup() {
        if(indicatorSize == 0) {
            indicatorSize = resources.getDimension(R.dimen.default_indicator_size).toInt()
        }

        val layoutParams = LinearLayout.LayoutParams(indicatorSize, indicatorSize)
        val margin = resources.getDimensionPixelSize(R.dimen.default_indicator_margins)
        layoutParams.apply {
            gravity = Gravity.CENTER_VERTICAL
            setMargins(margin, 0, margin, 0)
        }
        setLayoutParams(layoutParams)
    }

    open fun onCheckedChange(checked: Boolean) {
        if(isChecked != checked) {
            if(isChecked) {
                if(mustAnimate) {
                    scale()
                } else {
                    scale(0)
                }
            } else {
                if(mustAnimate) {
                    descale()
                } else {
                    descale(0)
                }
            }

            isChecked = checked
        }
    }

    fun scale() {
        scale(ANIMATION_DURATION)
    }

    fun scale(duration: Int) {
        val scaleAnimation = ScaleAnimation(1f, 1.1f, 1f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        scaleAnimation.duration = duration.toLong()
        scaleAnimation.fillAfter = true
        startAnimation(scaleAnimation)
    }

    fun descale() {
        descale(ANIMATION_DURATION)
    }

    fun descale(duration: Int) {
        val scaleAnimation = ScaleAnimation(1.1f, 1f, 1.1f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        scaleAnimation.duration = duration.toLong()
        scaleAnimation.fillAfter = true
        startAnimation(scaleAnimation)
    }

    open fun setMustAnimateChange(mustAnimateChange: Boolean) {
        mustAnimate = mustAnimateChange
    }
}