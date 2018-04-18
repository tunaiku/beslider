package app.beslider.com.interfaces

import android.support.annotation.DrawableRes
import android.widget.ImageView

interface LoadImage {
    fun loadImage(url: String, imageView: ImageView)
    fun loadImage(@DrawableRes resource: Int, imageView: ImageView)
    fun loadImage(url: String, @DrawableRes placeholder: Int, @DrawableRes errorDrawable: Int, imageView: ImageView)
}