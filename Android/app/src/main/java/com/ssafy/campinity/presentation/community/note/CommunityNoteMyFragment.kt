package com.ssafy.campinity.presentation.community.note

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.ApplicationClass
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentCommunityNoteMyBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class CommunityNoteMyFragment :
    BaseFragment<FragmentCommunityNoteMyBinding>(R.layout.fragment_community_note_my) {

    private val communityNoteViewModel by activityViewModels<CommunityNoteViewModel>()
    private val communityNoteListAdapter by lazy {
        CommunityNoteListAdapter(this::getPost)
    }

    override fun initView() {
        initObserver()
        Log.d("tlqkf", "initView: my")
    /*
        CoroutineScope(Dispatchers.Main).launch {
            val deffered: Deferred<Int> = async {
                communityCampsiteViewModel.getCampsiteMessageBriefInfoByScope(
                    mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude,
                    mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude,
                    recentCampsite.campsiteId,
                    mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude,
                    mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude
                )
                1
            }
            deffered.await()
            drawPostBox(recentCampsite)
        }*/
    }

    override fun onResume() {
        super.onResume()
        initNote()
        Log.d("tlqkf", "onResume: my")
    }

    private fun initObserver() {
        communityNoteViewModel.noteMyQuestions.observe(viewLifecycleOwner) { response ->
            response?.let {
                communityNoteListAdapter.setNote(it)
                Log.d("tlqkf", "initObserver: $it")
                binding.rvCommunityMyNote.apply {
                    adapter = communityNoteListAdapter
                    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                }
            }
        }
    }

    private fun initNote() {
        /*binding.rvCommunityMyNote.apply {
            adapter = communityNoteListAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }*/
        communityNoteViewModel.getNoteMyQuestions(ApplicationClass.preferences.userRecentCampsiteId.toString())
    }

    private fun getPost(questionId: String) {
        navigate(
            CommunityNoteFragmentDirections.actionCommunityNoteFragmentToCommunityNoteDetailFragment(
                questionId
            )
        )
    }
}