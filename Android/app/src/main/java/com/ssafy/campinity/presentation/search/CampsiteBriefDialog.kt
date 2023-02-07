package com.ssafy.campinity.presentation.search

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.campinity.common.util.getDeviceHeightPx
import com.ssafy.campinity.common.util.getDeviceWidthPx
import com.ssafy.campinity.databinding.DialogCampsiteBriefBinding
import com.ssafy.campinity.domain.entity.search.CampsiteBriefInfo

class CampsiteBriefDialog(
    context: Context,
    private val campsiteBriefData: CampsiteBriefInfo,
    private val actionSearchMainFragmentToSearchPostboxFragment: () -> Unit
) : Dialog(context) {

    private lateinit var binding: DialogCampsiteBriefBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogCampsiteBriefBinding.inflate(layoutInflater)
        initDialog()
        initListener()
        setContentView(binding.root)
    }

    private fun initDialog() {
        setCancelable(true)
        binding.item = campsiteBriefData
        binding.apply {
            rvCampsiteImage.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            rvCampsiteImage.adapter = CampsiteBriefImageAdapter(campsiteBriefData.images)
        }
    }

    private fun initListener() {
        binding.btnPostbox.setOnClickListener {
            actionSearchMainFragmentToSearchPostboxFragment()
        }
    }
}