package com.ssafy.campinity.presentation.search

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.common.util.LinearItemDecoration
import com.ssafy.campinity.databinding.FragmentSearchPostboxBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import com.ssafy.campinity.presentation.community.note.CommunityNoteDialogInterface
import com.ssafy.campinity.presentation.community.note.CommunityNoteQuestionDialog
import com.ssafy.campinity.presentation.community.note.CommunityNoteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchPostboxFragment :
    BaseFragment<FragmentSearchPostboxBinding>(R.layout.fragment_search_postbox),
    CommunityNoteDialogInterface {

    private lateinit var campsiteId: String
    private val arg by navArgs<SearchPostboxFragmentArgs>()
    private val searchViewModel by activityViewModels<SearchViewModel>()
    private val communityNoteViewModel by activityViewModels<CommunityNoteViewModel>()
    private val searchPostboxAdapter by lazy { SearchPostboxAdapter(listOf(), this::getPost) }

    override fun initView() {
        campsiteId = arg.campsiteId
        initFragment()
        initListener()
    }

    private fun initFragment() {
        binding.rvMyLetter.apply {
            layoutManager = LinearLayoutManager(
                requireContext(), RecyclerView.VERTICAL, false
            )
            adapter = searchPostboxAdapter

            addItemDecoration(
                LinearItemDecoration(requireContext(), RecyclerView.VERTICAL, 15)
            )

            lifecycleScope.launch {
                val sync = searchViewModel.getLetters(campsiteId)

                if (sync) {
                    searchPostboxAdapter.setLetters(searchViewModel.letters.value!!)
                    searchPostboxAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun initListener() {
        binding.btnPostBoxBack.setOnClickListener {
            popBackStack()
        }

        binding.tvMakePost.setOnClickListener {
            CommunityNoteQuestionDialog(
                requireContext(),
                campsiteId,
                this@SearchPostboxFragment
            ).show()
        }
    }

    override fun postNote(id: String, content: String) {
        CoroutineScope(Dispatchers.IO).launch {
            if (communityNoteViewModel.postNoteQuestion(id, content)) {
                withContext(Dispatchers.Main) {
                    showToast("질문이 등록되었습니다.")
                    searchViewModel.getLetters(campsiteId)
                    searchPostboxAdapter.setLetters(searchViewModel.letters.value!!)
                    searchPostboxAdapter.notifyItemInserted(0)
                }
            } else {
                withContext(Dispatchers.Main) {
                    showToast("질문 등록이 실패하였습니다.")
                }
            }
        }
    }

    private fun getPost(questionId: String) {
        navigate(
            SearchPostboxFragmentDirections.actionSearchPostboxFragmentToSearchLetterDetailFragment(
                questionId
            )
        )
    }
}