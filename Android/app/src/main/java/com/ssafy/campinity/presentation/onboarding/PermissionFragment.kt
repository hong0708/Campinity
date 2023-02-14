package com.ssafy.campinity.presentation.onboarding

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.navigation.NavDirections
import com.ssafy.campinity.ApplicationClass
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentPermissionBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PermissionFragment : BaseFragment<FragmentPermissionBinding>(R.layout.fragment_permission) {

    private val actionTrue: NavDirections =
        PermissionFragmentDirections.actionPermissionFragmentToHomeFragment()
    private val actionFalse: NavDirections =
        PermissionFragmentDirections.actionPermissionFragmentToOnboardingFragment()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun initView() {
        checkPermission()
        setButtonClickListener()
    }

    private fun setButtonClickListener() {
        binding.apply {
            btnConfirm.setOnClickListener {
                if (ApplicationClass.preferences.isLoggedIn) {
                    navigate(actionTrue)
                } else {
                    navigate(actionFalse)
                }
            }
        }
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (ApplicationClass.preferences.isLoggedIn) {
                navigate(actionTrue)
            } else {
                navigate(actionFalse)
            }
        }
    }
}