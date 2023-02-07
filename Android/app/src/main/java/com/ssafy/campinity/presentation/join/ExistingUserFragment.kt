package com.ssafy.campinity.presentation.join

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.ssafy.campinity.ApplicationClass
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentExistingUserBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import com.ssafy.campinity.presentation.mypage.MyPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExistingUserFragment :
    BaseFragment<FragmentExistingUserBinding>(R.layout.fragment_existing_user) {

    private val myPageViewModel by activityViewModels<MyPageViewModel>()

    override fun initView() {
        myPageViewModel.getInfo()
        binding.tvContentExistingUser.text =
            String.format(resources.getString(R.string.content_existing_user), ApplicationClass.preferences.nickname)
        viewLifecycleOwner.lifecycleScope.launch {
            delay(1000)
            navigate(ExistingUserFragmentDirections.actionExistingUserFragmentToHomeFragment())
        }
    }
}