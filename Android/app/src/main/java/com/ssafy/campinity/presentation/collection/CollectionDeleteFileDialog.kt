package com.ssafy.campinity.presentation.collection

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.DialogCollectionFileDeleteBinding

class CollectionDeleteFileDialog(
    context: Context,
    private val listener: FileDeleteDialogListener
) : Dialog(context) {

    private lateinit var binding:  DialogCollectionFileDeleteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_collection_file_delete,
            null, false
        )

        setContentView(binding.root)

        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
        )
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(true)
        setCancelable(true)

        binding.apply {
            btnConfirm.setOnClickListener {
                listener.onButtonClicked()
                dismiss()
            }
            btnCancel.setOnClickListener {
                dismiss()
            }
        }
    }
}