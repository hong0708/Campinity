package com.ssafy.campinity.common.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.TextView

class CustomDialog(
    context: Context,
    private val customDialogInterface: CustomDialogInterface,
    private val resID: Int,
    private val cancelBtnId: Int,
    private val confirmButton: Int
) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(resID)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val cancelButton = findViewById<ImageView>(cancelBtnId)
        cancelButton.setOnClickListener {
            dismiss()
        }

        val confirmButton = findViewById<TextView>(confirmButton)
        confirmButton.setOnClickListener {
            dismiss()
            customDialogInterface.onFinishButton()
        }
    }
}