package app.beslider.com.utils.viewholder

import android.support.annotation.DrawableRes
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import app.beslider.com.ui.Slider

class ImageSliderViewHolder(var imageView : ImageView) : RecyclerView.ViewHolder(imageView) {
    fun bindImageSlide(imageUrl: String) {
        Slider.imageLoadingService.loadImage(imageUrl, imageView);
    }

    fun bindImageSlide(@DrawableRes imageResourceId: Int) {
        Slider.imageLoadingService.loadImage(imageResourceId, imageView)
    }

    fun bindImageSlide(imageUrl: String, @DrawableRes placeHolder: Int, @DrawableRes error: Int) {
        Slider.imageLoadingService.loadImage(imageUrl, placeHolder, error, imageView)
    }
}