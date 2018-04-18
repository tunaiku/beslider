package app.beslider.com.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import app.beslider.com.R

class Builder(val mContext: Context) {
    val mConfig = Config()

    fun hideIndicators(hideIndicator: Boolean): Builder {
        mConfig.hideIndicators = hideIndicator
        return this
    }

    fun loopSlides(loopSlides: Boolean) : Builder {
        mConfig.loopSlides = loopSlides
        return this
    }

    fun indicatorSize(size: Int): Builder {
        mConfig.indicatorSize = size
        return this
    }

    fun selectedIndicator(indicator: Drawable): Builder {
        mConfig.selectedIndicator = indicator
        return this
    }

    fun unselectedIndicator(indicator: Drawable): Builder {
        mConfig.unselectedIndicator = indicator
        return this
    }

    fun animateIndicators(animate: Boolean): Builder {
        mConfig.animatedIndicator = animate
        return this
    }

    fun slideInterval(interval: Int): Builder {
        mConfig.slideInterval = interval
        return this
    }

    fun emptyImage(emptyImage: Int): Builder {
        mConfig.emptyImage = emptyImage
        return this
    }

    fun build(): Config {
        if(mConfig.selectedIndicator == null) {
            mConfig.selectedIndicator = ContextCompat.getDrawable(mContext, R.drawable.ic_default_indicator_selected)!!
        }

        if(mConfig.unselectedIndicator == null) {
            mConfig.unselectedIndicator = ContextCompat.getDrawable(mContext, R.drawable.ic_default_indicator_unselected)!!
        }

        if(mConfig.indicatorSize == Config.NO_SELECTED_IMAGE) {
            mConfig.indicatorSize = mContext.resources.getDimensionPixelSize(R.dimen.default_indicator_size)
        }

        return mConfig
    }
}