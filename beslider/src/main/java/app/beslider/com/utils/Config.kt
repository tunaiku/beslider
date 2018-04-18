package app.beslider.com.utils

import android.graphics.drawable.Drawable

class Config {
    companion object {
        val NO_SELECTED_IMAGE = -1
    }
    var hideIndicators = false
    var loopSlides = true
    var animatedIndicator = true
    var slideInterval = 0
    var indicatorSize = NO_SELECTED_IMAGE
    var emptyImage = NO_SELECTED_IMAGE
    var selectedIndicator : Drawable? = null
    var unselectedIndicator: Drawable? = null
}