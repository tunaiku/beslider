package app.beslider.com.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import app.beslider.com.R
import app.beslider.com.adapters.PositionController
import app.beslider.com.adapters.SliderAdapter
import app.beslider.com.adapters.SliderRecyclerViewAdapter
import app.beslider.com.helper.SnapHelper
import app.beslider.com.interfaces.LoadImage
import app.beslider.com.interfaces.OnSlideChangeListener
import app.beslider.com.interfaces.OnSlideClickListener
import app.beslider.com.utils.Config
import app.beslider.com.utils.SlideIndicatorsGroup
import app.beslider.com.utils.Builder
import java.util.*

class Slider : FrameLayout {

    var mContext: Context
    var recyclerView: RecyclerView
    var config: Config? = null
    var mAttributeSet: AttributeSet? = null
    var timer = Timer()
    var onSlideChangeListener: OnSlideChangeListener? = null
    var onSlideClickListener: OnSlideClickListener? = null
    var slideIndicatorsGroup: SlideIndicatorsGroup? = null
    var emptyView: View? = null
    var positionController: PositionController? = null
    var adapter: SliderRecyclerViewAdapter? = null
    var sliderAdapter: SliderAdapter? = null
    var pendingPosition = RecyclerView.NO_POSITION
    var selectedSlidePosition = 0

    constructor(context: Context) : super(context){
        mContext = context
        recyclerView = RecyclerView(mContext)
        setupView(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){
        mContext = context
        recyclerView = RecyclerView(mContext)
        mAttributeSet = attrs
        setupView(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        mContext = context
        recyclerView = RecyclerView(mContext)
        mAttributeSet = attrs
        setupView(attrs)
    }

    companion object {
        lateinit var imageLoadingService: LoadImage
        fun init(loadImage: LoadImage) {
            imageLoadingService = loadImage

        }
    }

    @SuppressLint("Recycle", "CustomViewStyleable")
    private fun setupView(attrs: AttributeSet?) {
        if(attrs == null) {
            config = Builder(mContext).build()
            setupDependency()
        } else {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.beslider)
            try {
                config = Builder(mContext).apply {
                    animateIndicators(typedArray.getBoolean(R.styleable.beslider_beslider_animateIndicators, true))
                    emptyImage(typedArray.getResourceId(R.styleable.beslider_beslider_emptyImage, Config.NO_SELECTED_IMAGE))
                    indicatorSize(typedArray.getDimensionPixelSize(R.styleable.beslider_beslider_indicatorSize, 0))
                    loopSlides(typedArray.getBoolean(R.styleable.beslider_beslider_loopSlides, false))
                    slideInterval(typedArray.getInteger(R.styleable.beslider_beslider_interval, 0))
                    selectedIndicator(typedArray.getDrawable(R.styleable.beslider_beslider_customSelectedIndicator))
                    unselectedIndicator(typedArray.getDrawable(R.styleable.beslider_beslider_customUnselectedIndicator))
                    hideIndicators(typedArray.getBoolean(R.styleable.beslider_beslider_hideIndicators, false))
                }.build()
                setupDependency()
            } catch (e: Exception) {
                Log.e("Beslider","SetupView: $e")
            } finally {
                typedArray.recycle()
            }
        }
    }

    private fun setupDependency() {
        setupRV()
        setupSlideIndicator()
    }

    private fun setupRV() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if(!config!!.loopSlides) return

                if(adapter != null && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    when(selectedSlidePosition) {
                        0 -> {
                            recyclerView!!.scrollToPosition(adapter!!.itemCount - 2)
                            onImageSlideChange(adapter!!.itemCount - 2)
                        }
                        adapter!!.itemCount - 1 -> {
                            recyclerView!!.scrollToPosition(1)
                            onImageSlideChange(1)
                        }
                    }
                }
            }
        })

        if(config!!.emptyImage != Config.NO_SELECTED_IMAGE) {
            emptyView = LayoutInflater.from(mContext).inflate(config!!.emptyImage, this, false)
            addView(emptyView)
        }
    }

    private fun setupSlideIndicator() {
        if(!config!!.hideIndicators) {
            slideIndicatorsGroup = SlideIndicatorsGroup(
                    mContext,
                    config!!.selectedIndicator,
                    config!!.unselectedIndicator,
                    0,
                    config!!.indicatorSize,
                    config!!.animatedIndicator
            )
        }
    }

    fun onImageSlideChange(position: Int) {
        selectedSlidePosition = position
        var userSlidePosition = 0

        if(positionController != null) {
            userSlidePosition = positionController!!.getUserSlidePosition(position)
        }

        if (slideIndicatorsGroup != null) {
            slideIndicatorsGroup!!.onSlideChange(userSlidePosition)
        }

        if (onSlideChangeListener != null) {
            onSlideChangeListener!!.onSlideChange(userSlidePosition)
        }
    }

    private fun setSelectedSlide(position: Int, animate: Boolean) {
        if(recyclerView.adapter != null) {
            if(animate) {
                recyclerView.smoothScrollToPosition(position)
            } else {
                recyclerView.scrollToPosition(position)
            }
            onImageSlideChange(position)
        } else {
            pendingPosition = position
        }
    }

    fun setSelectedSlide(position: Int) {
        if(positionController != null) {
            setSelectedSlide(positionController!!.getRealSlidePosition(position), true)
        }
    }

    private fun onAdapterAttached() {
        if(pendingPosition != RecyclerView.NO_POSITION) {
            recyclerView.smoothScrollToPosition(pendingPosition)
            onImageSlideChange(pendingPosition)
            pendingPosition = RecyclerView.NO_POSITION
        }
    }

    fun setSlideChangeListener(listener: OnSlideChangeListener) {
        onSlideChangeListener = listener
    }

    fun setSlideClickListener(listener: OnSlideClickListener) {
        onSlideClickListener = listener
        if(adapter != null) adapter!!.setSlideClickListener(listener)
    }

    fun getAdapter(): SliderAdapter? {
        return sliderAdapter
    }

    fun setAdapter(adapterSlider: SliderAdapter) {
        if(config == null) {
            setupView(mAttributeSet)
        }

        sliderAdapter = adapterSlider

        if (indexOfChild(recyclerView) == -1) {
            if (layoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
                recyclerView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            } else {
                recyclerView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            }

            addView(recyclerView)
        }

        recyclerView.isNestedScrollingEnabled = false
        val linearLayoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = linearLayoutManager

        if(sliderAdapter != null) {
            positionController = PositionController(sliderAdapter!!, config!!.loopSlides)
            adapter = SliderRecyclerViewAdapter(
                    sliderAdapter!!,
                    sliderAdapter!!.getItemCount() > 1 && config!!.loopSlides,
                    recyclerView.layoutParams,
                    object : OnTouchListener {
                        @SuppressLint("ClickableViewAccessibility")
                        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                            if(event!!.action == MotionEvent.ACTION_DOWN) {
                                stopTimer()
                            } else if(event.action == MotionEvent.ACTION_CANCEL || event.action == MotionEvent.ACTION_UP){
                                startTimer()
                            }
                            return false
                        }
                    }, positionController!!)

            recyclerView.adapter = adapter
            positionController!!.setRecyclerViewAdapter(adapter!!)
        }

        selectedSlidePosition = if(config!!.loopSlides) 1 else 0
        recyclerView.scrollToPosition(selectedSlidePosition)
        onImageSlideChange(selectedSlidePosition)
        pendingPosition = RecyclerView.NO_POSITION
        onAdapterAttached()

        val snapHelper = SnapHelper(object : SnapHelper.OnSelectedItemChange {
            override fun onSelectedItemChange(position: Int) {
                onImageSlideChange(position)
            }
        })

        recyclerView.onFlingListener = null
        snapHelper.attachToRecyclerView(recyclerView)

        if(slideIndicatorsGroup != null && sliderAdapter != null) {
            if(sliderAdapter!!.getItemCount() > 1) {
                if (indexOfChild(slideIndicatorsGroup) == -1) {
                    addView(slideIndicatorsGroup)
                }
                slideIndicatorsGroup!!.setSlides(sliderAdapter!!.getItemCount())
                slideIndicatorsGroup!!.onSlideChange(0)
                refreshIndicators()
            }
        }

        if(emptyView != null) {
            emptyView!!.visibility = View.GONE
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startTimer()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopTimer()
    }

    private fun startTimer() {
        if(config != null && config!!.slideInterval > 0) {
            stopTimer()
            timer = Timer()
            timer.schedule(SliderTimerTask(), config!!.slideInterval.toLong(), config!!.slideInterval.toLong())
        }
    }

    private fun stopTimer() {
        timer.cancel()
        timer.purge()
    }

    inner class SliderTimerTask : TimerTask() {
        override fun run() {
            if (mContext is Activity) {
                (mContext as Activity).runOnUiThread {
                    if(positionController != null) {
                        val nextSlidePosition = positionController!!.getNextSlide(selectedSlidePosition)
                        recyclerView.smoothScrollToPosition(nextSlidePosition)
                        onImageSlideChange(nextSlidePosition)
                    }
                }
            }
        }
    }

    fun setInterval(interval: Int) {
        config!!.slideInterval = interval
        stopTimer()
        startTimer()
    }

    fun setLoopSlides(loopSlides: Boolean) {
        config!!.loopSlides = loopSlides
        adapter!!.loop = loopSlides
        positionController!!.loop = loopSlides
        adapter!!.notifyDataSetChanged()
        recyclerView.scrollToPosition(if(loopSlides) 1 else 0)
        onImageSlideChange(if(loopSlides) 1 else 0)
    }

    fun setAnimateIndicators(shouldAnimate: Boolean) {
        config!!.animatedIndicator = shouldAnimate
        slideIndicatorsGroup?.setMustAnimateIndicators(shouldAnimate)
    }

    fun hideIndicators() {
        config!!.hideIndicators = true
        slideIndicatorsGroup?.visibility = View.GONE
    }

    fun showIndicators() {
        config!!.hideIndicators = false
        slideIndicatorsGroup?.visibility = View.VISIBLE
    }

    fun setIndicatorSize(indicatorSize: Int) {
        config!!.indicatorSize = indicatorSize
        refreshIndicators()
    }

    fun setSelectedIndicator(selectedIndicator: Drawable) {
        config!!.selectedIndicator = selectedIndicator
        refreshIndicators()
    }

    fun setUnSelectedIndicator(unselectedIndicator: Drawable) {
        config!!.unselectedIndicator = unselectedIndicator
        refreshIndicators()
    }

    private fun refreshIndicators() {
        if(!config!!.hideIndicators) {

            if(slideIndicatorsGroup != null) {
                removeView(slideIndicatorsGroup)
            }

            slideIndicatorsGroup = SlideIndicatorsGroup(
                    mContext,
                    config!!.selectedIndicator,
                    config!!.unselectedIndicator,
                    0,
                    config!!.indicatorSize,
                    config!!.animatedIndicator
            )

            addView(slideIndicatorsGroup)

            for (i in 0 until sliderAdapter!!.getItemCount()) {
                slideIndicatorsGroup?.onSlideAdd()
            }
        }
    }

    fun setDefaultSelectedIndicator() {
        config!!.selectedIndicator = ContextCompat.getDrawable(mContext, R.drawable.ic_default_indicator_selected)!!
        refreshIndicators()
    }

    fun setDefaultUnselectedIndicator() {
        config!!.unselectedIndicator = ContextCompat.getDrawable(mContext, R.drawable.ic_default_indicator_unselected)!!
        refreshIndicators()
    }
}