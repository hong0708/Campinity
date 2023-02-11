package com.ssafy.campinity.presentation.community.campsite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.campinity.ApplicationClass
import com.ssafy.campinity.databinding.ActivityReceiveHelpNoteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReceiveHelpNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReceiveHelpNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReceiveHelpNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.tvContent.text = ApplicationClass.preferences.helpContent
    }
}