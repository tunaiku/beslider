# Welcome to BeSlider

BeSlider is an easy to use library for making beautiful Android Image Slider. This library is build with Kotlin.


# How To Download

### Gradle
> Add it in your root build.gradle at the end of repositories:

    allprojects {  
	    repositories {  
	        ...
	        maven { url 'https://jitpack.io' }
	    }  
	}
 
> Add the dependency

    dependencies {
		implementation 'com.github.tunaiku:beslider:1.0'
	}

# How To Use
### XML

> 
    <app.beslider.com.ui.Slider
        android:layout_width="match_parent"  
        android:layout_height="wrap_content" />

### Kotlin - Main Activity

> 1. Create the adapter that extend to slider adapter

First step, create an adapter to adapt your data model with the slider.

    class BesliderAdapter (val mListImages : ArrayList<String?>) : SliderAdapter() {  
        override fun getItemCount(): Int {  
            return mListImages.size  
        }  
        override fun onBindImageSlide(position: Int, imageSliderViewHolder: ImageSliderViewHolder) {
            imageSliderViewHolder.bindImageSlide(mListImages[position]!!) 
        }  
    }

> 2. Create you image downloading service

Specify your image loading service. For example, if you work with Picasso in your project, you must implement LoadImage interface:

    class BesliderLoadingService : LoadImage {  
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

> 3. Initialize the slider in you application project

    // BesliderLoadingService for example is your Image Loading Service
    Slider.init(BesliderLoadingService())

> 4. Set your adapter on Slider

    // myBeSlider is ID of your slider layout and import with Kotlin Extension.
    // Example: import kotlinx.android.synthetic.main.activity_main.*
    myBeSlider.setAdapter(BesliderAdapter(mListImages))

> 5. Add OnClickListener to your slider

    myBeSlider.setSlideClickListener(object : OnSlideClickListener{ 
         override fun onSlideClick(position: Int) {  
              Toast.makeText(this@MainActivity, "Image Position $position clicked", Toast.LENGTH_SHORT).show()  
         }  
    })

## Customize

> Change slides interval (in milliseconds). For example: change slides for 3 second each. 

    app:beslider_interval="3000"


> Loop slides

    app:beslider_loopSlides="true"

> Change indicator size

    app:beslider_indicatorSize="8dp"

> Indicators are animated in default

    app:beslider_animateIndicators="true"

> Set default image for first show

    app:beslider_defaultImage="1"

> Add empty view when image not loaded

    app:beslider_emptyImage="@layout/empty_view"

> If you want to use custom indicator

    app:beslider_customSelectedIndicator="@drawable/ic_banner_indicator_selected"  
    app:beslider_customUnselectedIndicator="@drawable/ic_banner_indicator_unselected"

## For full example:
### XML
> 1. Add this to your activity_main.xml

    <app.beslider.com.ui.Slider  
	    android:id="@+id/beslider"  
	    android:layout_width="match_parent"  
	    android:layout_height="350dp"  
	    android:background="@drawable/banner_preloader"  
	    app:beslider_animateIndicators="true"  
	    app:beslider_defaultImage="1"  
	    app:beslider_emptyImage="@layout/empty_view"  
	    app:beslider_indicatorSize="8dp"  
	    app:beslider_interval="5000"  
	    app:beslider_loopSlides="true"  
	    app:beslider_customSelectedIndicator="@drawable/ic_banner_indicator_selected"  
	    app:beslider_customUnselectedIndicator="@drawable/ic_banner_indicator_unselected"/>

> 2. Empty view layout: empty_view.xml

    <LinearLayout 
	    xmlns:android="http://schemas.android.com/apk/res/android"  
	    android:layout_width="match_parent"
	    android:layout_height="match_parent"  
	    android:orientation="vertical"  
	    android:background="@color/colorDarkPrimary"  
	    android:gravity="center">  
  
	    <ProgressBar  
		    android:id="@+id/progress_bar"  
		    android:layout_width="50dp"  
		    android:layout_height="50dp"  
		    android:layout_gravity="center"  
		    android:indeterminate="true"  
		    android:indeterminateDrawable="@drawable/progressbar" />
		
	</LinearLayout>


### MainActivity.kt

> 1. Setup init slider in onCreate()

    override fun onCreate(savedInstanceState: Bundle?) {  
	    super.onCreate(savedInstanceState)  
	    
	    //.....
	    
	    Slider.init(BesliderLoadingService())
        loadbanner()
	}

> 2. Create function for load the banner. mListImages is ArrayList<String?>()

    fun loadbanner() {
	    // bannerSlider is loaded using kotlin extension
	    bannerSlider.setAdapter(BesliderAdapter(mListImages))
	    bannerSlider.setSlideClickListener(object : OnSlideClickListener {
		    override fun onSlideClick(position: Int) {
			    toast("Image position $position clicked")
		    }
	    })
    }

> 3. Build your project. :)


## Proguard Setting

> Add this code to your proguard-rules.pro

    -dontwarn app.beslider.com.adapters.**  
    -keep class app.beslider.com.adapters.** { *; }

## References

 - [Picasso](http://square.github.io/picasso/)
 - [AndroidImageSlider - daimajia](https://github.com/daimajia/AndroidImageSlider)
 - [BannerSlider - saeedsh92](https://github.com/saeedsh92/Banner-Slider)
