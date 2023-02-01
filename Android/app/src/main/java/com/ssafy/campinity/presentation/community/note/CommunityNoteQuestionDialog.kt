package com.ssafy.campinity.presentation.community.note

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import com.ssafy.campinity.data.remote.datasource.note.NoteQuestionRequest
import com.ssafy.campinity.databinding.DialogWriteNoteQuestionBinding

class CommunityNoteQuestionDialog(
    context: Context,
    private val communityNoteQuestionDialogInterface: CommunityNoteQuestionDialogInterface
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
                val noteQuestionRequest = NoteQuestionRequest(
                    "613f51f2-8942-4d84-bb60-7dc29b3487a6",
                    etInputMakeQuestion.text.toString()
                )
                communityNoteQuestionDialogInterface.postNoteQuestion(noteQuestionRequest)
                dismiss()
            }
            tvCancelEventNoteMarker.setOnClickListener { dismiss() }
        }
    }
}