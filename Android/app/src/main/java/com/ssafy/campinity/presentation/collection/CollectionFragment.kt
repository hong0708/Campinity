package com.ssafy.campinity.presentation.collection

import android.view.View
import android.view.animation.LinearInterpolator
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

    private val cardStackAdapter by lazy { CardStackAdapter(createItems(), this::getCollection) }
    private val gridAdapter by lazy { GridAdapter(this::getCollection) }
    private val manager by lazy { CardStackLayoutManager(context, this) }

    override fun initView() {
        initRecyclerView()
        initCardStackView()
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

    private fun paginate() {
        val old = cardStackAdapter.getSpots()
        val new = old.plus(createItems())
        val callback = CollectionDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        cardStackAdapter.setSpots(new)
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
    }

    private fun initRecyclerView() {
        binding.rvCollection.adapter = gridAdapter
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

    private fun createItems(): List<CollectionItem> {
        val items = ArrayList<CollectionItem>()
        items.apply {
            add(
                CollectionItem(
                    "1",
                    R.drawable.bg_collection_dummy_1,
                    "캠핑장",
                    "2023/01/19",
                    "내용을 입력해주세요ㅇㅇㅇㅇ"
                )
            )
            add(
                CollectionItem(
                    "1",
                    R.drawable.bg_collection_dummy_2,
                    "캠핑장",
                    "2023/01/19",
                    "내용을 입력해주세요ㅇㅇㅇㅇ"
                )
            )
            items.add(
                CollectionItem(
                    "1",
                    R.drawable.bg_collection_dummy_3,
                    "캠핑장",
                    "2023/01/19",
                    "내용을 입력해주세요ㅇㅇㅇㅇ"
                )
            )
            add(
                CollectionItem(
                    "1",
                    R.drawable.bg_collection_dummy_1,
                    "캠핑장",
                    "2023/01/19",
                    "내용을 입력해주세요ㅇㅇㅇㅇ"
                )
            )
            add(
                CollectionItem(
                    "1",
                    R.drawable.bg_collection_dummy_3,
                    "캠핑장",
                    "2023/01/19",
                    "내용을 입력해주세요ㅇㅇㅇㅇ"
                )
            )
            return items
        }
    }
}