package com.ssafy.campinity.presentation.onboarding

import androidx.lifecycle.lifecycleScope
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentExistingUserBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExistingUserFragment :
    BaseFragment<FragmentExistingUserBinding>(R.layout.fragment_existing_user) {
    override fun initView() {
        viewLifecycleOwner.lifecycleScope.launch {
            delay(1000)
            navigate(ExistingUserFragmentDirections.actionExistingUserFragmentToHomeFragment())
        }
    }
}