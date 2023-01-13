package com.sssafy.campinity.presentation.onboarding

import android.Manifest
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentPermissionBinding
import com.sssafy.campinity.common.util.isGranted
import com.sssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PermissionFragment : BaseFragment<FragmentPermissionBinding>(R.layout.fragment_permission) {

    private val action: NavDirections = PermissionFragmentDirections.actionPermissionFragmentToOnboardingFragment()

    override fun initView() {
        setButtonClickListener()

        if (requireContext().isGranted(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_NOTIFICATION_POLICY
            )) {
            requireView().findNavController().navigate(action)
        }
    }

    fun setButtonClickListener() {
        binding.btnConfirm.setOnClickListener {
            requireView().findNavController().navigate(action)
        }
    }
}