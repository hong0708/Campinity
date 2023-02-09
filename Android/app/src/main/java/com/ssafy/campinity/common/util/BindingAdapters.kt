package com.ssafy.campinity.common.util

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.ssafy.campinity.R

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("android:profileImgUri")
    fun ImageView.setProfileImg(imgUri: Uri?) {
        glide(context, imgUri, null, R.drawable.ic_profile_image)
            .circleCrop()
            .into(this)
    }

    @JvmStatic
    @BindingAdapter("android:collectionImgUri")
    fun ImageView.setCollectionImgUri(imgUri: Uri?) {
        glide(context, imgUri, null, null).into(this)
    }

    @JvmStatic
    @BindingAdapter("android:profileImgString")
    fun ImageView.setProfileImgString(imgUri: String?) {
        glide(
            context,
            "http://i8d101.p.ssafy.io:8003/images$imgUri",
            null,
            R.drawable.ic_profile_default
        ).circleCrop().into(this)
    }

    @JvmStatic
    @BindingAdapter("android:normalImgUri")
    fun ImageView.setNormalImg(imgUri: String?) {
        glide(
            context,
            "http://i8d101.p.ssafy.io:8003/images$imgUri",
            null,
            R.drawable.bg_image_not_found
        ).into(this)
    }

    @JvmStatic
    @BindingAdapter("android:roundedImgUri")
    fun ImageView.setRoundedImg(imgUri: String?) {
        glide(
            context,
            "http://i8d101.p.ssafy.io:8003/images$imgUri",
            null,
            R.drawable.bg_image_not_found
        ).apply(RequestOptions.bitmapTransform(RoundedCorners(10.px(context))))
            .into(this)
    }
}