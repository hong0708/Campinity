package com.ssafy.campinity.presentation.community.campsite

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.campinity.ApplicationClass
import com.ssafy.campinity.common.util.showToastMessage
import com.ssafy.campinity.databinding.ActivityReceiveHelpNoteBinding
import com.ssafy.campinity.presentation.community.CommunityActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ReceiveHelpNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReceiveHelpNoteBinding
    private val receiveHelpNoteViewModel by viewModels<ReceiveHelpNoteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReceiveHelpNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initListener()
    }

    private fun initView() {
        binding.tvContent.text = ApplicationClass.preferences.helpContent
    }

    private fun initListener() {
        binding.btnReceive.setOnClickListener {
            receiveHelpNoteViewModel.assigned.observe(this) {
                if (it == false) {
                    showToastMessage("다음 기회를 노려보세요!")
                } else {
                    showToastMessage("당첨!")
                }
            }
            receiveHelpNoteViewModel.replyHelp(
                ApplicationClass.preferences.fcmMessageId.toString(),
                ApplicationClass.preferences.fcmToken.toString()
            )
            showToastMessage("도움 신청이 완료되었습니다.")
            onBackPressed()
            val i = Intent(this, CommunityActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(i)
        }
        binding.btnCancel.setOnClickListener {
            val i = Intent(this, CommunityActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(i)
        }
    }
}