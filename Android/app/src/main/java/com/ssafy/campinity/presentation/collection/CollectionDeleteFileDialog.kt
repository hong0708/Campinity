package com.ssafy.campinity.presentation.collection

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.DialogCollectionFileDeleteBinding

class CollectionDeleteFileDialog(
    context: Context,
    private val listener: CollectionDeleteDialogListener
) : Dialog(context) {

    private lateinit var binding: DialogCollectionFileDeleteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_collection_file_delete,
            null, false
        )

        setContentView(binding.root)

        binding.apply {
            btnConfirm.setOnClickListener {
                listener.onSubmitButtonClicked()
                dismiss()
            }
            btnCancel.setOnClickListener {
                dismiss()
            }
        }
    }
}