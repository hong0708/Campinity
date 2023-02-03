package com.ssafy.campinity.presentation.onboarding

import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentPermissionBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PermissionFragment : BaseFragment<FragmentPermissionBinding>(R.layout.fragment_permission) {

    // 로그인 스킵하는 액션 로그인 제외하고 테스트 시 이용
    /*private val action: NavDirections =
        PermissionFragmentDirections.actionPermissionFragmentToHomeFragment()*/
    private val action: NavDirections =
        PermissionFragmentDirections.actionPermissionFragmentToOnboardingFragment()

    override fun initView() {
        setButtonClickListener()
    }

    private fun setButtonClickListener() {
        binding.btnConfirm.setOnClickListener {
            requireView().findNavController().navigate(action)
        }
    }
}