package com.ssafy.campinity.presentation.onboarding

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.ssafy.campinity.R
import com.ssafy.campinity.data.remote.datasource.auth.AuthRequest
import com.ssafy.campinity.databinding.FragmentOnboardingBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingFragment : BaseFragment<FragmentOnboardingBinding>(R.layout.fragment_onboarding) {

    private val onBoardingViewModel by viewModels<OnBoardingViewModel>()
    private val callback = initKakaoLoginCallback()
    private val actionFalse: NavDirections =
        OnBoardingFragmentDirections.actionOnBoardingFragmentToJoinFragment()
    private val actionTrue: NavDirections =
        OnBoardingFragmentDirections.actionOnBoardingFragmentToExistingUserFragment()

    override fun initView() {
        initListener()
        initBanner()
        observeOnBoardingViewModel()
    }

    private fun initKakaoLoginCallback(): (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e("login", "카카오계정으로 로그인 실패", error)
        } else if (token != null) {
            Log.i("login", "카카오계정으로 로그인 성공 ${token.accessToken}")
            onBoardingViewModel.requestLogin(AuthRequest(token.accessToken))
            if (onBoardingViewModel.refreshToken.value == "") {
                onBoardingViewModel.requestLogin(AuthRequest(token.accessToken))
            }
        }
    }

    private fun initListener() {
        binding.btnKakaoLogin.setOnClickListener {
            kakaoLogin()
        }
    }

    private fun initBanner() {
        binding.apply {
            vpBanner.adapter = OnBoardingAdapter(this@OnBoardingFragment)
            ciBanner.setViewPager(binding.vpBanner)
        }
    }

    private fun kakaoLogin() {
        UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = callback)
    }

    private fun observeOnBoardingViewModel() {
        onBoardingViewModel.isExist.observe(viewLifecycleOwner) {
            when (it) {
                true -> {
                    navigate(actionTrue)
                }
                false -> {
                    navigate(actionFalse)
                }
                else -> {}
            }
        }
    }
}