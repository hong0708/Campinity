package com.ssafy.campinity.common.util

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.ssafy.campinity.R

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("android:profileImgUri")
    fun ImageView.setProfileImg(imgUri: Uri?) {
        Glide.with(this.context)
            .load(imgUri)
            .placeholder(R.drawable.ic_profile_image)
            .error(R.drawable.ic_profile_image)
            .circleCrop()
            .into(this)
    }

    @JvmStatic
    @BindingAdapter("android:normalImgUri")
    fun ImageView.setNormalImg(imgUri: String?) {
        Glide.with(this.context)
            .load("http://i8d101.p.ssafy.io:8003/images$imgUri")
            .placeholder(R.drawable.bg_image_not_found)
            .error(R.drawable.bg_image_not_found)
            .into(this)
    }
}