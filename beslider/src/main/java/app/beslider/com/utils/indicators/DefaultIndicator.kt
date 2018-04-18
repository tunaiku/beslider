package app.beslider.com.utils.indicators

import android.content.Context
import android.support.v4.content.res.ResourcesCompat
import app.beslider.com.R

class DefaultIndicator(
        mContext: Context,
        mIndicatorSize: Int,
        mustAnimate: Boolean
) : IndicatorShape(mContext, mIndicatorSize, mustAnimate) {

    init {
        setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_default_indicator_unselected, null))
        val layoutParams = layoutParams
        if(layoutParams != null) {
            layoutParams.width = resources.getDimensionPixelSize(R.dimen.default_indicator_width)
            setLayoutParams(layoutParams)
        }
    }

    override fun onCheckedChange(checked: Boolean) {
        super.onCheckedChange(checked)
        if (checked) {
            setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_default_indicator_selected, null))
        } else {
            setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_default_indicator_unselected, null))
        }

    }

}