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
