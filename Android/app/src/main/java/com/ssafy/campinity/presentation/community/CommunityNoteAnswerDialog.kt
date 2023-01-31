package com.ssafy.campinity.presentation.community

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import com.ssafy.campinity.data.remote.datasource.note.NoteQuestionRequest
import com.ssafy.campinity.databinding.DialogWriteNoteQuestionBinding

class CommunityNoteAnswerDialog(
    context: Context,
    private val communityNoteAnswerDialogInterface: CommunityNoteAnswerDialogInterface
) : Dialog(context) {

    private lateinit var binding: DialogWriteNoteQuestionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogWriteNoteQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
        )
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        //context.dialogResize(this, 0.8F, 0.6F)
        setCanceledOnTouchOutside(true)
        setCancelable(true)
        binding.apply {
            tvMakeEventNoteMarker.setOnClickListener {
                val noteQuestionRequest = NoteQuestionRequest(
                    "68c156cf-3db2-41dd-8e4e-2e3b44d15179",
                    etInputMakeNote.text.toString()
                )
                communityNoteAnswerDialogInterface.postNoteQuestion(noteQuestionRequest)
                dismiss()
            }
            tvCancelEventNoteMarker.setOnClickListener { dismiss() }
        }
    }
}