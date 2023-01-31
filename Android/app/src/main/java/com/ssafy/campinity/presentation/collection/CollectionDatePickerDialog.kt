package com.ssafy.campinity.presentation.collection

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.DialogDatepickerBinding

class CollectionDatePickerDialog(
    context: Context,
    private val listener: CollectionDatePickerDialogListener
) : Dialog(context) {

    private lateinit var binding: DialogDatepickerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_datepicker,
            null, false
        )

        setContentView(binding.root)

        binding.apply {
            tvConfirm.setOnClickListener {
                listener.onSubmitButtonClickled(
                    String.format(
                        context.getString(R.string.content_collection_date),
                        dpCollection.year,
                        dpCollection.month + 1,
                        dpCollection.dayOfMonth
                    )
                )
                dismiss()
            }
            tvCancel.setOnClickListener {
                dismiss()
            }
        }
    }
}