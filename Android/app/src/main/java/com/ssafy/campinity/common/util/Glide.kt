package com.ssafy.campinity.common.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy

data class Size(
    val widthPX: Int,
    val heightPX: Int?
) {
    constructor(sizePx: Int) : this(sizePx, null)
}

fun preload(context: Context, url: String, size: Size?) {
    Glide.with(context)
        .load(url).let {
            if (size == null)
                it.preload()
            else
                it.preload(size.widthPX, size.heightPX!!)
        }
}

fun glide(
    context: Context,
    uri: Uri?,
    size: Size?,
    defaultImageId: Int?
): RequestBuilder<Drawable> {
    return Glide.with(context)
        .load(uri).let {
            return@let if (size == null)
                it
            else if (size.heightPX == null)
                it.override(size.widthPX)
            else
                it.override(size.widthPX, size.heightPX)
        }.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .dontAnimate().let {
            return@let if (defaultImageId == null)
                it
            else
                it.placeholder(defaultImageId)
                    .error(defaultImageId)
                    .fallback(defaultImageId)
        }
}

fun glide(
    context: Context,
    url: String?,
    size: Size?,
    defaultImageId: Int?
): RequestBuilder<Drawable> {
    return Glide.with(context)
        .load(url).let {
            return@let if (size == null)
                it
            else if (size.heightPX == null)
                it.override(size.widthPX)
            else
                it.override(size.widthPX, size.heightPX)
        }.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .dontAnimate().let {
            return@let if (defaultImageId == null)
                it
            else
                it.placeholder(defaultImageId)
                    .error(defaultImageId)
                    .fallback(defaultImageId)
        }
}

fun glide(
    context: Context,
    imageId: Int?,
    size: Size?,
    defaultImageId: Int?
): RequestBuilder<Drawable> {
    return Glide.with(context)
        .load(imageId).let {
            return@let if (size == null)
                it
            else if (size.heightPX == null)
                it.override(size.widthPX)
            else
                it.override(size.widthPX, size.heightPX)
        }.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .dontAnimate().let {
            return@let if (defaultImageId == null)
                it
            else
                it.placeholder(defaultImageId)
                    .error(defaultImageId)
                    .fallback(defaultImageId)
        }
}