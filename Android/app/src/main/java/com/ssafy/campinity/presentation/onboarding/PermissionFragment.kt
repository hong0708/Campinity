package com.ssafy.campinity.presentation.onboarding

import android.Manifest
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.ssafy.campinity.R
import com.ssafy.campinity.common.util.isGranted
import com.ssafy.campinity.databinding.FragmentPermissionBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PermissionFragment : BaseFragment<FragmentPermissionBinding>(R.layout.fragment_permission) {

    // 로그인 스킵하는 액션 로그인 제외하고 테스트 시 이용
//    private val action: NavDirections =
//        PermissionFragmentDirections.actionPermissionFragmentToHomeFragment()
    private val action: NavDirections =
        PermissionFragmentDirections.actionPermissionFragmentToOnboardingFragment()

    override fun initView() {
        setButtonClickListener()

        if (requireContext().isGranted(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_NOTIFICATION_POLICY
            )
        ) {
            requireView().findNavController().navigate(action)
        }
    }

    private fun setButtonClickListener() {
        binding.btnConfirm.setOnClickListener {
            requireView().findNavController().navigate(action)
        }
    }
}