package com.ssafy.campinity.presentation.search

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.campinity.databinding.DialogCampsiteBriefBinding
import com.ssafy.campinity.domain.entity.search.CampsiteBriefInfo

class CampsiteBriefDialog(
    context: Context,
    private val campsiteBriefData: CampsiteBriefInfo,
    private val navigationToSearchPostboxFragment: () -> Unit,
    private val navigationToCampsiteDetailFragment: (String) -> Unit
) : Dialog(context) {

    private lateinit var binding: DialogCampsiteBriefBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogCampsiteBriefBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDialog()
        initListener()
    }

    private fun initDialog() {
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
        )
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(true)
        setCancelable(true)

        binding.item = campsiteBriefData
        binding.apply {
            rvCampsiteImage.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            rvCampsiteImage.adapter = CampsiteBriefImageAdapter(context, campsiteBriefData.images)
        }
    }

    private fun initListener() {
        binding.btnPostbox.setOnClickListener {
            dismiss()
            navigationToSearchPostboxFragment()
        }
        binding.clParent.setOnClickListener {
            dismiss()
            navigationToCampsiteDetailFragment(campsiteBriefData.campsiteId)
        }
    }
}