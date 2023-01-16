package com.ssafy.campinity.presentation.search

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.ActivityCampsiteInfoBinding

class CampsiteInfoActivity : AppCompatActivity() {
    private val binding: ActivityCampsiteInfoBinding by lazy {
        ActivityCampsiteInfoBinding.inflate(
            layoutInflater
        )
    }
    private lateinit var campsiteImages: Array<Uri>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_campsite_info)

        binding.vpCampingSiteImage.offscreenPageLimit = 1
        binding.vpCampingSiteImage.adapter = CampsiteImageAdapter(this, campsiteImages)

        //viewpager activity에 연결
    }
}