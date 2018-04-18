package app.beslider.com.adapters

import app.beslider.com.utils.SliderType
import app.beslider.com.utils.viewholder.ImageSliderViewHolder

abstract class SliderAdapter {

    abstract fun getItemCount() : Int

    open fun getSliderType(position: Int) : SliderType {
        return SliderType.IMAGE
    }

    abstract fun onBindImageSlide(position: Int, imageSliderViewHolder: ImageSliderViewHolder)
}