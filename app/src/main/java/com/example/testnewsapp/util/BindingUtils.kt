package com.example.testnewsapp.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load
import com.example.testnewsapp.R


/**
 * @author Abhradeep Ghosh
 */

class BindingUtils {

    companion object {

        /**
         * Utility function to load an image
         */
        @JvmStatic
        @BindingAdapter("image")
        fun loadImage(view: ImageView, url: String?) {
            view.load(url) {
                crossfade(true)
                placeholder(R.drawable.placeholder)
                fallback(R.drawable.placeholder)
                error(R.drawable.placeholder)
            }
        }

        @JvmStatic
        @BindingAdapter("visibility")
        fun setVisibility(view: View, value: Boolean) {
            view.visibility = if (value) View.VISIBLE else View.GONE
        }

    }

}