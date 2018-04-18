package app.beslider.com.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import app.beslider.com.interfaces.OnSlideClickListener
import app.beslider.com.utils.viewholder.ImageSliderViewHolder

class SliderRecyclerViewAdapter(
        var iSliderAdapter: SliderAdapter,
        var loop: Boolean,
        var imageViewLayoutParams: ViewGroup.LayoutParams,
        var itemOnTouchListener: View.OnTouchListener,
        var positionController: PositionController
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var onSlideClickListener: OnSlideClickListener

    fun setSlideClickListener(listener: OnSlideClickListener) {
        onSlideClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val imageView = ImageView(parent.context)
        imageView.apply {
            layoutParams = imageViewLayoutParams
            adjustViewBounds = true
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
        return ImageSliderViewHolder(imageView)
    }

    override fun getItemCount(): Int {
        val addPlus = if(loop) 2 else 0
        return iSliderAdapter.getItemCount().plus(addPlus)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(!loop) {
            iSliderAdapter.onBindImageSlide(position, holder as ImageSliderViewHolder)
        } else {
            when (position) {
                0 -> {
                    iSliderAdapter.onBindImageSlide(positionController.getLastUserSlidePosition(), holder as ImageSliderViewHolder)
                }
                getItemCount() - 1 -> {
                    iSliderAdapter.onBindImageSlide(positionController.getFirstUserSlidePosition(), holder as ImageSliderViewHolder)
                }
                else -> {
                    iSliderAdapter.onBindImageSlide(position - 1, holder as ImageSliderViewHolder)
                }
            }
        }

        holder.itemView.setOnClickListener {
            onSlideClickListener.onSlideClick(positionController.getUserSlidePosition(holder.adapterPosition))
        }

        holder.itemView.setOnTouchListener(itemOnTouchListener)
    }
}