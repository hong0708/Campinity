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
}