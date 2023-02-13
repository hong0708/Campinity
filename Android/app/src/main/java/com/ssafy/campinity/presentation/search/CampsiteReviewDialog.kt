package com.ssafy.campinity.presentation.search

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import com.ssafy.campinity.R
import com.ssafy.campinity.common.util.showToastMessage
import com.ssafy.campinity.databinding.DialogWriteCampsiteReviewBinding

class CampsiteReviewDialog(
    context: Context,
    private val campsiteId: String,
    private val campsiteReviewDialogInterface: CampsiteReviewDialogInterface
) : Dialog(context) {

    private lateinit var binding: DialogWriteCampsiteReviewBinding
    private var rate = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogWriteCampsiteReviewBinding.inflate(layoutInflater)
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
            tvWriteReviewButton.setOnClickListener {
                if (etReviewContent.text.toString() == "")
                    context.showToastMessage("리뷰 내용을 입력해주세요.")
                else {
                    campsiteReviewDialogInterface.postReview(
                        campsiteId,
                        etReviewContent.text.toString(),
                        rate
                    )
                    dismiss()
                }
            }

            ivCancelReviewDialog.setOnClickListener {
                dismiss()
            }

            ibReviewCount1.setOnClickListener {
                if (rate == 0) {
                    rate = 1
                    ibReviewCount1.setBackgroundResource(R.drawable.ic_star_on)
                    ibReviewCount2.setBackgroundResource(R.drawable.ic_star_off)
                    ibReviewCount3.setBackgroundResource(R.drawable.ic_star_off)
                    ibReviewCount4.setBackgroundResource(R.drawable.ic_star_off)
                    ibReviewCount5.setBackgroundResource(R.drawable.ic_star_off)
                } else {
                    rate = 0
                    ibReviewCount1.setBackgroundResource(R.drawable.ic_star_off)
                    ibReviewCount2.setBackgroundResource(R.drawable.ic_star_off)
                    ibReviewCount3.setBackgroundResource(R.drawable.ic_star_off)
                    ibReviewCount4.setBackgroundResource(R.drawable.ic_star_off)
                    ibReviewCount5.setBackgroundResource(R.drawable.ic_star_off)
                }
            }

            ibReviewCount2.setOnClickListener {
                rate = 2
                ibReviewCount1.setBackgroundResource(R.drawable.ic_star_on)
                ibReviewCount2.setBackgroundResource(R.drawable.ic_star_on)
                ibReviewCount3.setBackgroundResource(R.drawable.ic_star_off)
                ibReviewCount4.setBackgroundResource(R.drawable.ic_star_off)
                ibReviewCount5.setBackgroundResource(R.drawable.ic_star_off)
            }

            ibReviewCount3.setOnClickListener {
                rate = 3
                ibReviewCount1.setBackgroundResource(R.drawable.ic_star_on)
                ibReviewCount2.setBackgroundResource(R.drawable.ic_star_on)
                ibReviewCount3.setBackgroundResource(R.drawable.ic_star_on)
                ibReviewCount4.setBackgroundResource(R.drawable.ic_star_off)
                ibReviewCount5.setBackgroundResource(R.drawable.ic_star_off)
            }

            ibReviewCount4.setOnClickListener {
                rate = 4
                ibReviewCount1.setBackgroundResource(R.drawable.ic_star_on)
                ibReviewCount2.setBackgroundResource(R.drawable.ic_star_on)
                ibReviewCount3.setBackgroundResource(R.drawable.ic_star_on)
                ibReviewCount4.setBackgroundResource(R.drawable.ic_star_on)
                ibReviewCount5.setBackgroundResource(R.drawable.ic_star_off)
            }

            ibReviewCount5.setOnClickListener {
                rate = 5
                ibReviewCount1.setBackgroundResource(R.drawable.ic_star_on)
                ibReviewCount2.setBackgroundResource(R.drawable.ic_star_on)
                ibReviewCount3.setBackgroundResource(R.drawable.ic_star_on)
                ibReviewCount4.setBackgroundResource(R.drawable.ic_star_on)
                ibReviewCount5.setBackgroundResource(R.drawable.ic_star_on)
            }
        }
    }
}