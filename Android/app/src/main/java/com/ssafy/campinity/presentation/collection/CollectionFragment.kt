package com.ssafy.campinity.presentation.collection

import android.view.View
import android.view.animation.LinearInterpolator
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentCollectionBinding
import com.ssafy.campinity.domain.entity.collection.CollectionItem
import com.ssafy.campinity.presentation.base.BaseFragment
import com.yuyakaido.android.cardstackview.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectionFragment : BaseFragment<FragmentCollectionBinding>(R.layout.fragment_collection),
    CardStackListener {

    private val collectionViewModel by viewModels<CollectionViewModel>()
    private val cardStackAdapter by lazy { CardStackAdapter(this::getCollection) }
    private val gridAdapter by lazy { GridAdapter(this::getCollection) }
    private val manager by lazy { CardStackLayoutManager(context, this) }
    private var pageState = true

    override fun initView() {
        initListener()
        initRecyclerView()
        initCardStackView()
    }

    private fun initListener() {
        binding.fabCollectionMode.setOnClickListener {
            if (pageState) {
                binding.clCardView.visibility = View.VISIBLE
                binding.clRecyclerView.visibility = View.GONE
            } else {
                binding.clCardView.visibility = View.GONE
                binding.clRecyclerView.visibility = View.VISIBLE
            }
            pageState = !pageState
        }
        binding.tvAddCollection.setOnClickListener {
            navigate(CollectionFragmentDirections.actionCollectionFragmentToCreateCollectionFragment())
        }
    }

    override fun onCardSwiped(direction: Direction?) {
        if (manager.topPosition == 1) {
            paginate()
        }
        when (direction) {
            Direction.Right -> {}
            Direction.Left -> {}
            else -> Unit
        }
    }

    override fun onCardRewound() {}

    override fun onCardCanceled() {}

    override fun onCardAppeared(view: View?, position: Int) {}

    override fun onCardDisappeared(view: View?, position: Int) {}

    override fun onCardDragging(direction: Direction?, ratio: Float) {}

    override fun onResume() {
        super.onResume()
        collectionViewModel.getCollections()
    }

    private fun paginate() {
        val old = cardStackAdapter.getItems()
        val new = old.plus(collectionViewModel.collectionListData.value!!)
        val callback = CollectionDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        cardStackAdapter.setItems(new)
        result.dispatchUpdatesTo(cardStackAdapter)
    }

    private fun initCardStackView() {
        manager.apply {
            setStackFrom(StackFrom.Right)
            setVisibleCount(3)
            setTranslationInterval(12.0f)
            setScaleInterval(0.90f)
            setSwipeThreshold(0.3f)
            setMaxDegree(20.0f)
            setDirections(Direction.HORIZONTAL)
            setCanScrollHorizontal(true)
            setCanScrollVertical(false)
            setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
            setOverlayInterpolator(LinearInterpolator())
        }

        binding.csvCollection.apply {
            layoutManager = manager
            adapter = cardStackAdapter
            itemAnimator.apply {
                if (this is DefaultItemAnimator) {
                    supportsChangeAnimations = true
                }
            }
        }
        collectionViewModel.collectionListData.observe(viewLifecycleOwner) { response ->
            response?.let { cardStackAdapter.setCollection(it) }
        }
        collectionViewModel.getCollections()
    }

    private fun initRecyclerView() {
        binding.rvCollection.adapter = gridAdapter
        binding.rvCollection.layoutManager =
            GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
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

    private fun createItems(): List<CollectionItem> {
        val items = ArrayList<CollectionItem>()
        items.apply {
            add(
                CollectionItem(
                    "1",
                    "https://search.naver.com/search.naver?where=image&sm=tab_jum&query=rkddkwl#",
                    "캠핑장",
                    "2023/01/19",
                    "https://search.naver.com/search.naver?where=image&sm=tab_jum&query=rkddkwl#"
                )
            )
            add(
                CollectionItem(
                    "1",
                    "https://search.naver.com/search.naver?where=image&sm=tab_jum&query=rkddkwl#",
                    "캠핑장",
                    "2023/01/19",
                    "내용을 입력해주세요ㅇㅇㅇㅇ"
                )
            )
            items.add(
                CollectionItem(
                    "1",
                    "https://search.naver.com/search.naver?where=image&sm=tab_jum&query=rkddkwl#",
                    "캠핑장",
                    "2023/01/19",
                    "내용을 입력해주세요ㅇㅇㅇㅇ"
                )
            )
            add(
                CollectionItem(
                    "1",
                    "https://search.naver.com/search.naver?where=image&sm=tab_jum&query=rkddkwl#",
                    "캠핑장",
                    "2023/01/19",
                    "내용을 입력해주세요ㅇㅇㅇㅇ"
                )
            )
            add(
                CollectionItem(
                    "1",
                    "https://search.naver.com/search.naver?where=image&sm=tab_jum&query=rkddkwl#",
                    "캠핑장",
                    "2023/01/19",
                    "내용을 입력해주세요ㅇㅇㅇㅇ"
                )
            )
            return items
        }
    }
}