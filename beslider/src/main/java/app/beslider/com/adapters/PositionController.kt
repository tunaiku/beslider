package app.beslider.com.adapters

import android.util.Log

class PositionController(var sliderAdapter: SliderAdapter, var loop : Boolean) {

    lateinit var sliderRecyclerViewAdapter: SliderRecyclerViewAdapter

    fun getUserSlidePosition(position: Int): Int {
        return if(loop) {
            when(position) {
                0 -> {
                    sliderRecyclerViewAdapter.itemCount - 3
                }
                sliderRecyclerViewAdapter.itemCount - 1 -> {
                    0
                }
                else -> {
                    position - 1
                }
            }
        } else {
            position
        }
    }

    fun getRealSlidePosition(position: Int): Int {
        return if (!loop) {
            position
        } else {
            if(position >= 0 && position < sliderAdapter.getItemCount()) {
                position + 1
            } else {
                Log.e("Beslider", "setRealSlidePosition: Invalid Item Position");
                1
            }
        }
    }

    fun getLastUserSlidePosition(): Int {
        return sliderAdapter.getItemCount() - 1
    }

    fun getFirstUserSlidePosition(): Int {
        return 0
    }

    fun setRecyclerViewAdapter(recyclerViewAdapter: SliderRecyclerViewAdapter) {
        sliderRecyclerViewAdapter = recyclerViewAdapter
    }

    fun getNextSlide(currentPosition: Int) : Int {
        return if(currentPosition < sliderRecyclerViewAdapter.itemCount - 1) {
            currentPosition + 1
        } else {
            if (loop) 1 else 0
        }
    }
}