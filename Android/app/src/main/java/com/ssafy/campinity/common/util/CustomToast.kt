package com.ssafy.campinity.common.util

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.ToastCustomBinding

object CustomToast {

    fun createToast(context: Context, message: String): Toast {
        val inflater = LayoutInflater.from(context)
        val binding: ToastCustomBinding =
            DataBindingUtil.inflate(inflater, R.layout.toast_custom, null, false)

        binding.tvContent.text = message

        return Toast(context).apply {
            setGravity(Gravity.BOTTOM or Gravity.CENTER, 0, 30.px(context))
            duration = Toast.LENGTH_LONG
            view = binding.root
        }
    }
}