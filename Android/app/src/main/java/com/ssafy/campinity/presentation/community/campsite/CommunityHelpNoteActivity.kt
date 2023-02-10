package com.ssafy.campinity.presentation.community.campsite

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.ssafy.campinity.common.util.showToastMessage
import com.ssafy.campinity.databinding.ActivityWriteHelpNoteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityHelpNoteActivity : AppCompatActivity() {

    private val args by navArgs<CommunityCampsiteDialogActivityArgs>()
    private lateinit var binding: ActivityWriteHelpNoteBinding
    private lateinit var type: String
    private lateinit var campsiteId: String
    private val viewModel by viewModels<CommunityHelpNoteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteHelpNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        type = args.dialogType
        campsiteId = args.campsiteId

        initView()
        observeState()
    }

    private fun initView() {
        binding.apply {
            btnMakeNoteHelp.setOnClickListener {
                if (etUserHelp.text.isEmpty() || etUserLocation.text.isEmpty()) {
                    showToastMessage("모든 정보를 입력해주세요.")
                } else {
                    viewModel.createHelpNoteMessage(
                        etUserHelp.text.toString(),
                        campsiteId,
                        etUserLocation.text.toString(),
                        128.41904925964056,
                        36.10680223224355,
                        type
                    )
                }
            }
        }
    }

    private fun observeState() {
        viewModel.isSucceed.observe(this) {
            when (it) {
                true -> {
                    onBackPressed()
                    showToastMessage("${viewModel.receiverNum}명이 메시지를 수신했어요!")
                }
                else -> {}
            }
        }
    }
}