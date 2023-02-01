package com.ssafy.campinity.presentation.community.note

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import com.ssafy.campinity.databinding.DialogWriteNoteQuestionAnswerBinding

class CommunityNoteAnswerDialog(
    context: Context,
    private val communityNoteAnswerDialogInterface: CommunityNoteAnswerDialogInterface,
    private val questionId: String,
    private val questionContent: String
) : Dialog(context) {
    private lateinit var binding: DialogWriteNoteQuestionAnswerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogWriteNoteQuestionAnswerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
        )
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setCanceledOnTouchOutside(true)
        setCancelable(true)
        binding.apply {
            tvNoteQuestionContent.text = questionContent
            tvMakeAnswerBtn.setOnClickListener {
                communityNoteAnswerDialogInterface.postNoteAnswer(
                    etInputMakeAnswer.text.toString(),
                    questionId,
                )
                dismiss()
            }
            tvCancelAnswerBtn.setOnClickListener { dismiss() }
        }
    }
}