package sample.beslider.app.utils

import android.content.Context
import android.widget.ImageView
import app.beslider.com.interfaces.LoadImage
import com.squareup.picasso.Picasso

class LoadImageService : LoadImage {
    override fun loadImage(url: String, imageView: ImageView) {
        Picasso.get().load(url).into(imageView);
    }

    override fun loadImage(resource: Int, imageView: ImageView) {
        Picasso.get().load(resource).into(imageView);
    }

    override fun loadImage(url: String, placeholder: Int, errorDrawable: Int, imageView: ImageView) {
        Picasso.get().load(url).placeholder(placeholder).error(errorDrawable).into(imageView);
    }
}