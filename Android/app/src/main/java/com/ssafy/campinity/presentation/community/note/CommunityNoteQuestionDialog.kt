package com.ssafy.campinity.presentation.community.note

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import com.ssafy.campinity.common.util.showToastMessage
import com.ssafy.campinity.databinding.DialogWriteNoteQuestionBinding

class CommunityNoteQuestionDialog(
    context: Context,
    private val campsiteId: String,
    private val communityNoteDialogInterface: CommunityNoteDialogInterface
) : Dialog(context) {

    private lateinit var binding: DialogWriteNoteQuestionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogWriteNoteQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
        )
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(true)
        setCancelable(true)

        binding.apply {
            tvMakeEventNoteMarker.setOnClickListener {
                if (etInputMakeQuestion.text.toString() == "")
                    context.showToastMessage("질문 내용을 입력해주세요.")
                else {
                    communityNoteDialogInterface.postNote(
                        campsiteId,
                        etInputMakeQuestion.text.toString()
                    )
                    dismiss()
                }
            }
            tvCancelEventNoteMarker.setOnClickListener { dismiss() }
        }
    }
}