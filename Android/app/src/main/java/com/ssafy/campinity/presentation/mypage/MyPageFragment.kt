package com.ssafy.campinity.presentation.mypage

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.ApplicationClass
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentMyPageBinding
import com.ssafy.campinity.domain.entity.community.NoteQuestionTitle
import com.ssafy.campinity.presentation.base.BaseFragment
import com.ssafy.campinity.presentation.community.note.CommunityNoteListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment :
    BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page),
    LogoutDialogListener {

    private val myPageViewModel by activityViewModels<MyPageViewModel>()
    private val communityNoteListAdapter by lazy {
        CommunityNoteListAdapter(this::showDialog)
    }

    override fun initView() {
        binding.vm = myPageViewModel
        setData()
        initRecyclerView()
        initListener()
        initSpinner()
    }

    private fun initListener() {
        binding.clEditProfile.setOnClickListener {
            navigate(MyPageFragmentDirections.actionMyPageFragmentToEditProfileFragment())
        }
        binding.ivArrowLeft.setOnClickListener {
            popBackStack()
        }
        binding.tvLogOut.setOnClickListener {
            val dialog = LogoutDialog(requireContext(), this)
            dialog.show()
        }
    }

    private fun initRecyclerView() {
        myPageViewModel.getNotes()
        binding.rvCommunityMyNote.apply {
            adapter = communityNoteListAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    private fun initSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.category_array,
            R.layout.spinner_txt
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerCategory.adapter = adapter
        }

        binding.spinnerCategory.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if (p0?.getItemAtPosition(p2).toString() == "자유") {
                        myPageViewModel.etcNotesListData.observe(viewLifecycleOwner) { response ->
                            response?.let {
                                communityNoteListAdapter.setNote(it.map { info ->
                                    NoteQuestionTitle(
                                        info.content,
                                        info.createdAt,
                                        info.messageId
                                    )
                                })
                            }
                        }
                    } else {
                        myPageViewModel.reviewNotesListData.observe(viewLifecycleOwner) { response ->
                            response?.let {
                                communityNoteListAdapter.setNote(it.map { info ->
                                    NoteQuestionTitle(
                                        info.content,
                                        info.createdAt,
                                        info.messageId
                                    )
                                })
                            }
                        }
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    return
                }
            }
    }

    private fun showDialog(noteQuestionId: String) {
        myPageViewModel.getDetailData(noteQuestionId)
        myPageViewModel.detailData.observe(viewLifecycleOwner) {
            val dialog = ReviewNoteDialog(requireContext(), it!!)
            dialog.show()
        }
    }

    private fun setData() {
        myPageViewModel.getInfo()

//        CoroutineScope(Dispatchers.IO).launch {
//            val deffered: Deferred<Int> = async {
//                myPageViewModel.getInfo()
//                1
//            }
//            deffered.await()
//        }
//        binding.tvNickname.text = myPageViewModel.userInfo.value?.name
//        Log.d("엥", "setData: ${myPageViewModel.userInfo.value?.name}")
//        Glide.with(requireContext())
//            .load("http://i8d101.p.ssafy.io:8003/images" + myPageViewModel.userInfo.value?.imagePath.toString())
//            .placeholder(R.drawable.ic_profile_default)
//            .error(R.drawable.ic_profile_default)
//            .circleCrop()
//            .into(binding.ivProfile)


//        CoroutineScope(Dispatchers.Main).launch {
//            CoroutineScope(Dispatchers.IO).launch {
//                myPageViewModel.getInfo()
//            }.join()
//            binding.tvNickname.text = myPageViewModel.userInfo.value?.name
//            Log.d("엥", "setData: ${myPageViewModel.userInfo.value?.name}")
//            Glide.with(requireContext())
//                .load("http://i8d101.p.ssafy.io:8003/images" + myPageViewModel.userInfo.value?.imagePath.toString())
//                .placeholder(R.drawable.ic_profile_default)
//                .error(R.drawable.ic_profile_default)
//                .circleCrop()
//                .into(binding.ivProfile)
//        }
    }

    override fun onSubmitButtonClicked() {
        myPageViewModel.requestLogout()
        myPageViewModel.isLoggedOut.observe(viewLifecycleOwner) {
            if (it == true) {
                ApplicationClass.preferences.clearPreferences()
                navigate(MyPageFragmentDirections.actionMyPageFragmentToOnBoardingFragment())
                Toast.makeText(requireContext(), "로그아웃되었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}