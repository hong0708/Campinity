package com.ssafy.campinity.presentation.community.campsite

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.campinity.ApplicationClass
import com.ssafy.campinity.R
import com.ssafy.campinity.common.util.showToastMessage
import com.ssafy.campinity.databinding.ActivityReceiveHelpNoteBinding
import com.ssafy.campinity.presentation.MainActivity
import com.ssafy.campinity.presentation.chat.ChatListActivity
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
                    val i = Intent(this, MainActivity::class.java)
                    i.putExtra("state", "no_assigned")
                    i.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(i)
                    finish()
                } else {
                    showToastMessage("선정되었습니다!")
                    val i = Intent(this, ChatListActivity::class.java)
                    i.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(i)
                    finish()
                }
            }
            receiveHelpNoteViewModel.replyHelp(
                ApplicationClass.preferences.fcmMessageId.toString(),
                ApplicationClass.preferences.fcmToken.toString()
            )
        }

        binding.btnCancel.setOnClickListener {
            val i = Intent(this, CommunityActivity::class.java)
            i.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(i)
        }

        binding.lottieReceiveHelpNote.apply {
            addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator?) {}

                override fun onAnimationEnd(p0: Animator?) {
                    binding.clLottieHelpNote.visibility = View.GONE
                    binding.clReceiveHelpNote.visibility = View.VISIBLE
                }

                override fun onAnimationCancel(p0: Animator?) {}

                override fun onAnimationRepeat(p0: Animator?) {}
            })
            setAnimation(R.raw.fcm_letter)
            speed = 0.5f
            visibility = View.VISIBLE
            playAnimation()
        }
    }
}