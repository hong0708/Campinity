package com.ssafy.campinity.presentation.collection

import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentCollectionBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectionFragment : BaseFragment<FragmentCollectionBinding>(R.layout.fragment_collection) {

    private val collectionAdapter by lazy {
        CollectionAdapter(this::getCollection)
    }

    override fun initView() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvCollection.adapter = collectionAdapter
        binding.rvCollection.layoutManager =
            GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
    }

    private fun getCollection(collectionId: String) {
        navigate(
            CollectionFragmentDirections.actionCollectionFragmentToCollectionDetailFragment(
                collectionId
            )
        )
    }
}