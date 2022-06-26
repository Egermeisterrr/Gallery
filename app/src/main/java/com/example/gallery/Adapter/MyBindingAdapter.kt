package com.example.gallery.Adapter


import android.widget.ImageView
import com.bumptech.glide.Glide


object MyBindingAdapter {
    fun setImageByGlide(view: ImageView, imgUrl: String?) {
        Glide.with(view.context)
            .load(imgUrl)
            .thumbnail(0.1f) // мб стоит удалить
            .into(view)
    }
}