package com.ssafy.campinity.presentation.community

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentCommunityNoteMyBinding
import com.ssafy.campinity.domain.entity.community.NoteQuestionTitle
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityNoteMyFragment :
    BaseFragment<FragmentCommunityNoteMyBinding>(R.layout.fragment_community_note_my) {

    private val impl = arrayListOf<String>("a", "b", "c")
    private lateinit var impls: List<NoteQuestionTitle>

    private val communityNoteViewModel by activityViewModels<CommunityNoteViewModel>()

    override fun initView() {
        initListener()
        communityNoteViewModel.requestNoteMyQuestions("1")
        observeCommunityNoteViewModel()
    }

    override fun onResume() {
        super.onResume()
        /*for (i in impls){
            impl.add(i.content)
        }*/

        //Log.d("note questions", "onResume: $impls")

        binding.rvCommunityMyNote.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = CommunityNoteListAdapter(impl)
        }
    }

    private fun initListener() {

    }

    private fun observeCommunityNoteViewModel() {
        communityNoteViewModel.noteMyQuestions.observe(viewLifecycleOwner) {
            when (it?.isEmpty()) {
                true -> {
                    Log.d("note questions", "observeCommunityNoteViewModel: ")
                    Log.d("note questions", "onResume: $impls")
                    impls = communityNoteViewModel.noteMyQuestions.value!!
                }
                false -> {
                    impls = communityNoteViewModel.noteMyQuestions.value!!
                    Log.d("note questions", "observeCommunityNoteViewModel: $it")
                    Log.d("note questions", "onResume: $impls")
                }
                else -> {
                    impls = communityNoteViewModel.noteMyQuestions.value!!
                    Log.d("note questions", "onResume: $impls")
                }
            }
        }
    }
}