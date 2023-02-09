package com.ssafy.campinity.common.util

import android.content.Context
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ssafy.campinity.R

fun preload(context: Context, url: String, widthPX: Int, heightPX: Int) {
    Log.d("GlideLog", "GlideLog preload")
    Glide.with(context)
        .load(url)
        .preload(widthPX, heightPX)
}

fun glide(context: Context, url: String, widthPX: Int, heightPX: Int, view: ImageView) {
    Glide.with(context)
        .load(url)
        .override(widthPX, heightPX)
        .fitCenter()
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .dontAnimate()
        .placeholder(R.drawable.bg_image_not_found)
        .error(R.drawable.bg_image_not_found)
        .fallback(R.drawable.bg_image_not_found)
        .into(view)
}

fun glide(context: Context, url: String, sizePx: Int, view: ImageView) {
    Glide.with(context)
        .load(url)
        .override(sizePx)
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .placeholder(R.drawable.bg_image_not_found)
        .error(R.drawable.bg_image_not_found)
        .fallback(R.drawable.bg_image_not_found)
        .into(view)
}

fun glide(context: Context, resourceId: Int, widthPX: Int, heightPX: Int, view: ImageView) {
    Glide.with(context)
        .load(resourceId)
        .override(widthPX, heightPX)
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .placeholder(R.drawable.bg_image_not_found)
        .error(R.drawable.bg_image_not_found)
        .fallback(R.drawable.bg_image_not_found)
        .into(view)
}

fun glide(context: Context, resourceId: Int, sizePx: Int, view: ImageView) {
    Glide.with(context)
        .load(resourceId)
        .override(sizePx)
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .placeholder(R.drawable.bg_image_not_found)
        .error(R.drawable.bg_image_not_found)
        .fallback(R.drawable.bg_image_not_found)
        .into(view)
}

fun imageNotFound(context: Context, widthPX: Int, heightPX: Int, view: ImageView) {
    Glide.with(context)
        .load(R.drawable.bg_image_not_found)
        .override(widthPX, heightPX)
        .centerCrop()
        .into(view)
}

fun imageNotFound(context: Context, sizePx: Int, view: ImageView) {
    Glide.with(context)
        .load(R.drawable.bg_image_not_found)
        .override(sizePx)
        .centerCrop()
        .into(view)
}