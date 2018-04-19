package sample.beslider.app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import app.beslider.com.interfaces.OnSlideClickListener
import app.beslider.com.ui.Slider
import com.squareup.picasso.Picasso
import sample.beslider.app.adapter.MySliderAdapter
import sample.beslider.app.utils.LoadImageService

class MainActivity : AppCompatActivity() {

    val mListImages = ArrayList<String?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addImages()
        loadSlider()
    }

    private fun addImages() {
        mListImages.add("https://i.annihil.us/u/prod/marvel/movies/blackpanther/social/assets/global_share_1200x630.jpg")
        mListImages.add("https://cdn.shopify.com/s/files/1/1211/8882/products/infanityWar_WEB_1024x1024.jpg?v=1522423111")
        mListImages.add("https://cdn.vox-cdn.com/thumbor/gI_IEw0_sFk9tnTf_pMJFJLjxYY=/0x0:1040x1300/1200x800/filters:focal(794x791:960x957)/cdn.vox-cdn.com/uploads/chorus_image/image/55235623/homecoming.0.jpg")
        mListImages.add("https://i.ndtvimg.com/i/2017-04/fast-8-cars_827x510_41492173294.jpg")
        mListImages.add("https://1.bp.blogspot.com/-EOpdq7-jSdU/VvVvbMX4MKI/AAAAAAAAJas/lVmDHC2KPnEepjTfjKunduZtcJ7YArnvA/s1600/iron-man-3-poster-exclusivo.jpg")
    }

    /**
     * mySlider is extension of beslider Id layout
     * using kotlin synthetic extension
     * import kotlinx.android.synthetic.main.activity_main.*
     */
    private fun loadSlider() {
        val beslider: Slider = findViewById(R.id.mySlider)
        Slider.init(LoadImageService())
        beslider.setAdapter(MySliderAdapter(mListImages))
        beslider.setSlideClickListener(object : OnSlideClickListener{
            override fun onSlideClick(position: Int) {
                Toast.makeText(this@MainActivity, "Image Position $position clicked", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
