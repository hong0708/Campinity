package com.ssafy.campinity.presentation.collection

import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentCollectionDetailBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CollectionDetailFragment :
    BaseFragment<FragmentCollectionDetailBinding>(R.layout.fragment_collection_detail) {

    private val args by navArgs<CollectionDetailFragmentArgs>()
    private val collectionViewModel by activityViewModels<CollectionViewModel>()

    override fun initView() {
        initListener()
        getData()
    }

    private fun initListener() {
        binding.ivArrowLeft.setOnClickListener { popBackStack() }
        binding.ivMultiOption.setOnClickListener {
            when (binding.clCollectionDeleteUpdate.isVisible) {
                true -> {
                    binding.clCollectionDeleteUpdate.visibility = View.GONE
                }
                false -> {
                    binding.clCollectionDeleteUpdate.visibility = View.VISIBLE
                    setDeleteUpdateView()
                }
            }
        }
    }

    private fun setDeleteUpdateView() {
        binding.tvCollectionDelete.setOnClickListener {

        }
        binding.tvCollectionUpdate.setOnClickListener {

        }
    }

    private fun getData() {
        collectionViewModel.getCollection(args.collectionId)
        collectionViewModel.collectionData.observe(viewLifecycleOwner) {
            binding.collectionDetail = it
        }
    }
}