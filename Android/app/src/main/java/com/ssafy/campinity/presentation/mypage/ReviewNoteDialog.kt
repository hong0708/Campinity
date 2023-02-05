package com.ssafy.campinity.presentation.mypage

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.DialogReviewNoteBinding
import com.ssafy.campinity.domain.entity.community.CampsiteMessageDetailInfo

class ReviewNoteDialog(context: Context, private val detailInfo: CampsiteMessageDetailInfo) : Dialog(context) {

    private lateinit var binding: DialogReviewNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_review_note,
            null, false
        )

        setContentView(binding.root)

        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
        )
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(true)
        setCancelable(true)


        binding.ivCloseWriteReviewNoteDialog.setOnClickListener { dismiss() }
        binding.detailInfo = detailInfo
    }
}