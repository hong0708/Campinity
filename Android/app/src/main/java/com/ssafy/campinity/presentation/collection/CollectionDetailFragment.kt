package com.ssafy.campinity.presentation.collection

import android.view.View
import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentCollectionDetailBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CollectionDetailFragment :
    BaseFragment<FragmentCollectionDetailBinding>(R.layout.fragment_collection_detail),
    FileDeleteDialogListener {

    private val args by navArgs<CollectionDetailFragmentArgs>()
    private val collectionViewModel by activityViewModels<CollectionViewModel>()

    override fun initView() {
        initListener()
        getData()
    }

    override fun onButtonClicked() {
        collectionViewModel.deleteCollection(args.collectionId)
        collectionViewModel.isDeleted.observe(viewLifecycleOwner) {
            if (it) showToast("컬렉션이 삭제되었습니다.")
        }
        popBackStack()
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
            val dialog = CollectionDeleteDialog(requireContext(), this)
            dialog.setCanceledOnTouchOutside(true)
            dialog.show()
            dialog.window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }
        binding.tvCollectionUpdate.setOnClickListener {
            navigate(
                CollectionDetailFragmentDirections.actionCollectionDetailFragmentToUpdateCollectionFragment(
                    args.collectionId
                )
            )
        }
    }

    private fun getData() {
        collectionViewModel.getCollection(args.collectionId)
        collectionViewModel.collectionData.observe(viewLifecycleOwner) {
            binding.collectionDetail = it
        }
    }
}