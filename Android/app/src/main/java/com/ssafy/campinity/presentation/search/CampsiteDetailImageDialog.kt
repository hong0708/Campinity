package com.ssafy.campinity.presentation.search

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ssafy.campinity.R
import com.ssafy.campinity.common.util.BindingAdapters.setCollectionImgUri
import com.ssafy.campinity.common.util.BindingAdapters.setRankingImg
import com.ssafy.campinity.common.util.getDeviceWidthPx
import com.ssafy.campinity.databinding.DialogCampsiteImageDetailBinding

class CampsiteDetailImageDialog(
    val activity: Activity,
    val id: String
) : Dialog(activity) {

    private lateinit var binding: DialogCampsiteImageDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogCampsiteImageDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
        )
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(true)
        setCancelable(true)

        binding.apply {
            //ivCampsiteImgDetail.setRankingImg(id)
            Log.d("wpqkfwha", "onCreate: $id")


            ivCampsiteImgDetail.let {
                Glide.with(activity)
                    .load("https://gocamping.or.kr/upload/camp/3003/1745wHLqxRTHUae2wDOLGGws.jpg")
                    .placeholder(R.drawable.bg_image_not_found)
                    .error(R.drawable.bg_image_not_found)
                    .into(it)
            }

            /*ivCampsiteImgDetail.let {
                Glide.with(it.context)
                    .load(id)
                    .override(getDeviceWidthPx(context))
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(it)
            }*/
            ivCloseImageDialog.setOnClickListener { dismiss() }
        }
    }
}