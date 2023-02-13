package com.ssafy.campinity.presentation.search

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import com.ssafy.campinity.databinding.DialogDeleteCampsiteReviewBinding

class CampsiteReviewDeleteDialog(
    context: Context,
    private val onConfirm: (String, Int) -> Unit,
    private val reviewId: String,
    private val position: Int
) : Dialog(context) {

    private lateinit var binding: DialogDeleteCampsiteReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogDeleteCampsiteReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
        )
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(true)
        setCancelable(true)

        initListener()
    }

    private fun initListener() {
        binding.apply {
            btnConfirm.setOnClickListener {
                onConfirm(reviewId, position)
                dismiss()
            }

            btnCancel.setOnClickListener { dismiss() }
        }
    }
}