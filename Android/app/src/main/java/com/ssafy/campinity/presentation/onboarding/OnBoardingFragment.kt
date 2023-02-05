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
        binding.btnKakaoLogin.setOnClickListener { kakaoLogin() }
    }

    private fun initBanner() {
        binding.apply {
            vpBanner.adapter = OnBoardingAdapter(this@OnBoardingFragment)
            ciBanner.setViewPager(binding.vpBanner)
        }
    }

    private fun kakaoLogin() {
//        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
//        if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
//            UserApiClient.instance.loginWithKakaoTalk(requireContext()) { token, error ->
//                if (error != null) {
//                    Log.e("login", "카카오톡으로 로그인 실패", error)
//                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
//                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
//                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
//                        return@loginWithKakaoTalk
//                    }
//                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
//                    UserApiClient.instance.loginWithKakaoAccount(
//                        requireContext(),
//                        callback = callback
//                    )
//                } else if (token != null) {
//                    Log.i("login", "카카오톡으로 로그인 성공 ${token.accessToken}")
//                    onBoardingViewModel.requestLogin(AuthRequest(token.accessToken))
//                }
//            }
//        } else {
//            UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = callback)
//        }
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