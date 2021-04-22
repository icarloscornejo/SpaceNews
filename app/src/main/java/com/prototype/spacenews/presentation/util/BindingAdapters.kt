package com.prototype.spacenews.presentation.util

import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.prototype.spacenews.R

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("glideSrc")
    fun ImageView.setImageWithGlide(src: String) {
        Glide.with(context)
            .load(src)
            .placeholder(AppCompatResources.getDrawable(context, R.mipmap.ic_space_news))
            .fitCenter()
            .into(this)
    }
}