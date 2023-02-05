package com.ssafy.campinity.presentation.community.campsite

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.ssafy.campinity.databinding.DialogWriteReviewNoteBinding

class CommunityCampsiteFreeReviewDialog(
    context: Context,
    private val typeOfDialog: String,
    private val communityCampsiteFreeReviewDialogInterface: CommunityCampsiteFreeReviewDialogInterface
) : Dialog(context) {

    private lateinit var binding: DialogWriteReviewNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogWriteReviewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
        )
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(true)
        setCancelable(true)

        binding.apply {
            tvReviewTitle.text = typeOfDialog

            ivCloseWriteReviewNoteDialog.setOnClickListener {
                dismiss()
            }

            ibAddPhoto.setOnClickListener { }

            if (typeOfDialog == "review") {
                tvSelectMarkerLocation.visibility = View.VISIBLE

                tvSelectMarkerLocation.setOnClickListener {
                    //CommunityCampsiteMarkerDialog(context).show()
                }

                tvMakeReview.setOnClickListener {
                    //communityCampsiteFreeReviewDialogInterface.createFreeReviewNote()
                }

            } else {
                tvSelectMarkerLocation.visibility = View.GONE

                tvMakeReview.setOnClickListener {
                    //communityCampsiteFreeReviewDialogInterface.createFreeReviewNote()
                }
            }
        }
    }
}