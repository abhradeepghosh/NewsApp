package com.example.testnewsapp.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load
import com.example.testnewsapp.R

/**
 * @author Abhradeep Ghosh
 */

class BindingUtils {

    companion object{

        @JvmStatic
        @BindingAdapter("image")
        fun loadImage(view: ImageView, url: String?){
            view.load(url){
                crossfade(true)
                placeholder(R.drawable.placeholder)
                fallback(R.drawable.placeholder)
                error(R.drawable.placeholder)
            }
        }
    }

}