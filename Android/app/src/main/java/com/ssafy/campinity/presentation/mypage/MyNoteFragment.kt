package com.ssafy.campinity.presentation.mypage

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentMyNoteBinding
import com.ssafy.campinity.domain.entity.community.NoteQuestionTitle
import com.ssafy.campinity.presentation.base.BaseFragment
import com.ssafy.campinity.presentation.community.note.CommunityNoteListAdapter

class MyNoteFragment : BaseFragment<FragmentMyNoteBinding>(R.layout.fragment_my_note) {

    private val myPageViewModel by activityViewModels<MyPageViewModel>()
    private val communityNoteListAdapter by lazy {
        CommunityNoteListAdapter(this::showDialog)
    }

    override fun initView() {
        initRecyclerView()
        initSpinner()
    }

    private fun initRecyclerView() {
        myPageViewModel.getNotes()
        binding.rvMyNote.apply {
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
                                if (response.isEmpty()) {
                                    binding.rvMyNote.visibility = View.GONE
                                    binding.clEmptyCollection.visibility = View.VISIBLE
                                } else {
                                    binding.clEmptyCollection.visibility = View.GONE
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
                    } else {
                        myPageViewModel.reviewNotesListData.observe(viewLifecycleOwner) { response ->
                            response?.let {
                                if (response.isEmpty()) {
                                    binding.rvMyNote.visibility = View.GONE
                                    binding.clEmptyCollection.visibility = View.VISIBLE
                                } else {
                                    binding.clEmptyCollection.visibility = View.GONE
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
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    return
                }
            }
    }

    private fun showDialog(noteQuestionId: String) {
        myPageViewModel.getDetailData(noteQuestionId)
    }
}