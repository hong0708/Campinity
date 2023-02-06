package com.ssafy.campinity.presentation.community.campsite

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.gms.common.util.CollectionUtils.isEmpty
import com.ssafy.campinity.R
import com.ssafy.campinity.common.util.getDeviceWidthPx
import com.ssafy.campinity.databinding.DialogReviewNoteBinding
import com.ssafy.campinity.domain.entity.community.CampsiteMessageDetailInfo

class CommunityCampsiteFreeReviewDetailDialog(
    context: Context,
    val messageDetailInfo: CampsiteMessageDetailInfo
) : Dialog(context) {
    private lateinit var binding: DialogReviewNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogReviewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
        )
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(true)
        setCancelable(true)

        initListener()
        initData()
    }

    private fun initData() {
        binding.apply {
            tvReviewContent.text = messageDetailInfo.content
            tvUserName.text = messageDetailInfo.authorName
            if (messageDetail?.messageCategory == "리뷰") {
                tvReviewTitle.text = "리뷰 쪽지"
            } else {
                tvReviewTitle.text = "자유 쪽지"
            }
            tvMessageDate.text = messageDetailInfo.createdAt
            //ivReviewImg.setImageURI(messageDetailInfo.imagePath)
            ivReviewImg.apply {
                if (messageDetailInfo.imagePath?.isEmpty() == true) {
                    Glide.with(context)
                        .load(R.drawable.bg_image_not_found)
                        .override(getDeviceWidthPx(context))
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .into(this)
                } else {
                    Glide.with(context)
                        .load("http://i8d101.p.ssafy.io:8003/images" + messageDetailInfo.imagePath)
                        .override(getDeviceWidthPx(context))
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .into(this)
                }
            }
        }
    }

    private fun initListener() {
        binding.apply {
            ivCloseWriteReviewNoteDialog.setOnClickListener { dismiss() }
        }
    }
}