package com.ssafy.campinity.presentation.onboarding

import android.os.Build
import androidx.annotation.RequiresApi
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
}