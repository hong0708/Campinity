package com.ssafy.campinity.presentation.search

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import com.ssafy.campinity.databinding.DialogReviewNoteBinding
import com.ssafy.campinity.domain.entity.community.CampsiteMessageDetailInfo

class SearchReviewNoteDetailDialog(
    context: Context,
    private val messageDetailInfo: CampsiteMessageDetailInfo
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
