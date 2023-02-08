package com.ssafy.campinity.presentation.collection

import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.ssafy.campinity.R
import com.ssafy.campinity.common.util.GridItemDecoration
import com.ssafy.campinity.databinding.FragmentCollectionBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectionFragment : BaseFragment<FragmentCollectionBinding>(R.layout.fragment_collection) {

    private val collectionViewModel by viewModels<CollectionViewModel>()
    private val collectionViewPagerAdapter by lazy { CollectionViewPagerAdapter(this::getCollection) }
    private val gridAdapter by lazy { GridAdapter(this::getCollection) }
    private var pageState = true

    override fun initView() {
        initRecyclerView()
        initViewPager()
        initListener()
    }

    private fun initListener() {
        binding.fabCollectionMode.setOnClickListener {
            if (pageState) {
                binding.apply {
                    clCardView.visibility = View.VISIBLE
                    clRecyclerView.visibility = View.GONE
                    fabCollectionMode.setImageResource(R.drawable.ic_collection_card)
                }
            } else {
                binding.apply {
                    clCardView.visibility = View.GONE
                    clRecyclerView.visibility = View.VISIBLE
                    fabCollectionMode.setImageResource(R.drawable.ic_collection_grid)
                }
            }
            pageState = !pageState
        }
        binding.tvAddCollection.setOnClickListener {
            navigate(CollectionFragmentDirections.actionCollectionFragmentToCreateCollectionFragment())
        }
    }

    override fun onResume() {
        super.onResume()
        collectionViewModel.getCollections()
    }

    private fun initViewPager() {
        val offsetBetweenPages =
            resources.getDimensionPixelOffset(R.dimen.offsetBetweenPages).toFloat()

        binding.vpCollection.apply {
            adapter = collectionViewPagerAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            offscreenPageLimit = 4
            setPageTransformer { page, position ->
                val myOffset = position * -(2 * offsetBetweenPages)
                if (position < -1) {
                    page.translationX = -myOffset
                } else if (position <= 1) {
                    val scaleFactor = 0.8f.coerceAtLeast(1 - kotlin.math.abs(position))
                    page.translationX = myOffset
                    page.scaleY = scaleFactor
                    page.alpha = scaleFactor
                } else {
                    page.alpha = 0f
                    page.translationX = myOffset
                }
            }
        }
        collectionViewModel.collectionListData.observe(viewLifecycleOwner) { response ->
            response?.let { collectionViewPagerAdapter.setCollection(it) }
        }
        collectionViewModel.getCollections()
    }

    private fun initRecyclerView() {
        binding.rvCollection.apply {
            adapter = gridAdapter
            layoutManager =
                GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            addItemDecoration(GridItemDecoration(context, 2, 12, 10))
        }
        collectionViewModel.collectionListData.observe(viewLifecycleOwner) { response ->
            response?.let { gridAdapter.setCollection(it) }
        }
        collectionViewModel.getCollections()
    }

    private fun getCollection(collectionId: String) {
        navigate(
            CollectionFragmentDirections.actionCollectionFragmentToCollectionDetailFragment(
                collectionId
            )
        )
    }
}