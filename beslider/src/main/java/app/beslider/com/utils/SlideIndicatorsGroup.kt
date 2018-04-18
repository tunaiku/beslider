package app.beslider.com.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import app.beslider.com.R
import app.beslider.com.interfaces.OnSlideChangeListener
import app.beslider.com.utils.indicators.DefaultIndicator
import app.beslider.com.utils.indicators.IndicatorShape

class SlideIndicatorsGroup(
        var mContext: Context,
        var selectedIndicator: Drawable?,
        var unselectedIndicator: Drawable?,
        var defaultIndicator: Int,
        var indicatorSize: Int,
        var mustAnimateIndicator: Boolean
) : LinearLayout(mContext), OnSlideChangeListener {

    var slidesCount = 0
    var indicatorShapeList = ArrayList<IndicatorShape>()

    init {
        setup()
    }

    fun setSlides(slideCount: Int) {
        removeAllViews()
        indicatorShapeList.clear()
        slidesCount = 0
        for (i in 0 until slidesCount) {
            onSlideAdd()
        }
        slidesCount = slideCount
    }

    fun onSlideAdd() {
        slidesCount += 1
        addIndicator()
    }

    fun addIndicator() {
        lateinit var indicatorShape: IndicatorShape
        if(selectedIndicator != null && unselectedIndicator != null) {
            indicatorShape = object : IndicatorShape(mContext, indicatorSize, mustAnimateIndicator) {
                override fun onCheckedChange(checked: Boolean) {
                    super.onCheckedChange(checked)
                    if(checked) {
                        background = selectedIndicator
                    } else {
                        background = unselectedIndicator
                    }
                }
            }

            indicatorShape.background = unselectedIndicator
            indicatorShapeList.add(indicatorShape)
            addView(indicatorShape)
        } else {
            indicatorShape = DefaultIndicator(mContext, indicatorSize, mustAnimateIndicator)
            indicatorShapeList.add(indicatorShape)
            addView(indicatorShape)
        }
    }

    fun setup() {
        orientation = LinearLayout.HORIZONTAL
        val layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val margin = resources.getDimensionPixelSize(R.dimen.default_indicator_margins) * 2
        layoutParams.apply {
            gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
            setMargins(0,0,0,margin)
        }
        setLayoutParams(layoutParams)
    }

    fun setMustAnimateIndicators(shouldAnimate: Boolean) {
        mustAnimateIndicator = shouldAnimate
        for (indicatorShape in indicatorShapeList) {
            indicatorShape.mustAnimate = shouldAnimate
        }
    }

    override fun onSlideChange(position: Int) {
        Log.d("SlideIndicatorGroup","onSlideChange: " + position)
        var loopPosition = 0
        for(indicatorShape in indicatorShapeList) {
            if(loopPosition == position) {
                indicatorShape.onCheckedChange(true)
            } else {
                indicatorShape.onCheckedChange(false)
            }
            loopPosition += 1
        }
    }

}