package com.ssafy.campinity.presentation.mypage

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.DialogReviewNoteBinding

class ReviewNoteDialog(context: Context) : Dialog(context) {

    private lateinit var binding: DialogReviewNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_review_note,
            null, false
        )

        setContentView(binding.root)

        binding.ivCloseWriteReviewNoteDialog.setOnClickListener { dismiss() }
    }
}