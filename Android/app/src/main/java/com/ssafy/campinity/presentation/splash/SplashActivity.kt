package com.ssafy.campinity.presentation.splash

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.ssafy.campinity.ApplicationClass
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.ActivitySplashBinding
import com.ssafy.campinity.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var fadeInAnim: Animation
    private val SPLASH_VIEW_TIME: Long = 5000
    private val splashViewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        setUserMap()
        moveSunImg()
        fadeOutBackground()
        checkTokenValidation()
        setNickname()
    }

    private fun setUserMap() {
        ApplicationClass.preferences.isUserInSearch = false.toString()
        ApplicationClass.preferences.isUserInCommunity = false.toString()
    }

    private fun setNickname() {
        splashViewModel.nickname.observe(this) {
            ApplicationClass.preferences.nickname = it
        }
        splashViewModel.getInfo()
    }

    private fun checkTokenValidation() {
        lifecycleScope.launch {
            delay(SPLASH_VIEW_TIME)
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun moveSunImg() {
        ObjectAnimator.ofFloat(binding.ivSun, View.TRANSLATION_Y, -400f).apply {
            duration = 3000
            start()
        }
    }

    private fun fadeOutBackground() {
        fadeInAnim = AnimationUtils.loadAnimation(this@SplashActivity, R.anim.fade_out)
        binding.ivFadeBackground.startAnimation(fadeInAnim)
        lifecycleScope.launch {
            delay(3000)
            binding.ivFadeBackground.visibility = View.GONE
        }
    }
}