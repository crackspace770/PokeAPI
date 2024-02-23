package com.fajar.pokeapi.core.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

object Utils {

    fun ImageView.loadImageUrl(url: String?) {
        Glide.with(this.context)
            .load(url)
            .into(this)
    }
}