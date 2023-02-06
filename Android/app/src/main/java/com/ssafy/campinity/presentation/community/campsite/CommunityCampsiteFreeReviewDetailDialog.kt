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
        binding.detailInfo = messageDetailInfo
    }

    private fun initListener() {
        binding.apply {
            ivCloseWriteReviewNoteDialog.setOnClickListener { dismiss() }
        }
    }
}