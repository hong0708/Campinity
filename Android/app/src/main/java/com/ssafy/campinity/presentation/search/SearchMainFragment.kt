package com.ssafy.campinity.presentation.search

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.ssafy.campinity.R
import com.ssafy.campinity.common.util.getDeviceHeightPx
import com.ssafy.campinity.common.util.px
import com.ssafy.campinity.data.remote.datasource.search.SearchFilterRequest
import com.ssafy.campinity.databinding.FragmentSearchMainBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchMainFragment : BaseFragment<FragmentSearchMainBinding>(R.layout.fragment_search_main) {

    private lateinit var callback: OnBackPressedCallback
    private lateinit var behaviorList: BottomSheetBehavior<LinearLayout>
    private lateinit var behaviorArea: BottomSheetBehavior<FragmentContainerView>
    private lateinit var behaviorFilter: BottomSheetBehavior<FragmentContainerView>
    private val searchViewModel by activityViewModels<SearchViewModel>()

    override fun initView() {
        behaviorList = BottomSheetBehavior.from(binding.llList)
        behaviorArea = BottomSheetBehavior.from(binding.fcvArea)
        behaviorFilter = BottomSheetBehavior.from(binding.fcvFilter)

        initListener()
        initBehaviorList()
        initBehaviorArea()
        initBehaviorFilter()
        observeStateBehavior()
        initBehaviorState()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.d("SearchMainFragment", "SearchMainFragment back pressed")
                searchViewModel.setStateBehaviorList(false)
                requireActivity().finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

    private fun initListener() {
        binding.rlShowList.setOnClickListener {
            behaviorList.state = BottomSheetBehavior.STATE_EXPANDED
            binding.clSearch.setBackgroundResource(R.drawable.bg_rect_bilbao_under_radius30)
            binding.rlSearchAgain.visibility = View.GONE
            binding.rlShowMap.visibility = View.VISIBLE
            binding.rlShowList.visibility = View.GONE
        }

        binding.rlShowMap.setOnClickListener {
            behaviorList.state = BottomSheetBehavior.STATE_COLLAPSED
            binding.viewEmptySpace.visibility = View.INVISIBLE
            binding.clSearch.background = null
            binding.rlSearchAgain.visibility = View.VISIBLE
            binding.rlShowList.visibility = View.VISIBLE
            binding.rlShowMap.visibility = View.GONE
        }

        binding.rlArea.setOnClickListener {
            binding.clSearch.visibility = View.GONE
            behaviorArea.state = BottomSheetBehavior.STATE_EXPANDED
        }

        binding.rlFilter.setOnClickListener {
            binding.clSearch.visibility = View.GONE
            behaviorFilter.state = BottomSheetBehavior.STATE_EXPANDED
        }

        binding.etSearchByName.apply {
            setOnEditorActionListener { textView, id, _ ->
                var handled = false

                if (id == EditorInfo.IME_ACTION_SEARCH) {
                    searchViewModel.getCampsitesByFiltering(SearchFilterRequest(keyword = textView.text.toString()))

                    this.clearFocus()
                    this.isFocusable = false
                    this.setText("")
                    this.isFocusableInTouchMode = true
                    this.isFocusable = true
                    val imm =
                        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(binding.etSearchByName.windowToken, 0)

                    handled = true
                }

                return@setOnEditorActionListener handled
            }
        }

        binding.rlSearchAgain.setOnClickListener {
            searchViewModel.setIsSearchAgain(true)
        }
    }

    private fun initBehaviorList() {
        behaviorList.addBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        binding.rlShowList.visibility = View.VISIBLE
                        binding.rlShowMap.visibility = View.GONE
                        searchViewModel.setStateBehaviorList(false)
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        binding.viewEmptySpace.visibility = View.VISIBLE
                        binding.clSearch.setBackgroundResource(R.drawable.bg_rect_bilbao_under_radius30)
                        binding.rlSearchAgain.visibility = View.GONE
                        searchViewModel.setStateBehaviorList(true)
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                        binding.viewEmptySpace.visibility = View.INVISIBLE
                        binding.clSearch.background = null
                        binding.rlSearchAgain.visibility = View.VISIBLE
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {}
                    BottomSheetBehavior.STATE_HIDDEN -> {}
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {}
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })
    }

    private fun initBehaviorArea() {
        var isDragging = false

        behaviorArea.addBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> isDragging = false
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        isDragging = false
                        searchViewModel.setStateBehaviorArea(true)
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> isDragging = true
                    BottomSheetBehavior.STATE_SETTLING -> {}
                    BottomSheetBehavior.STATE_HIDDEN -> {}
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {}
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if (isDragging) if (slideOffset <= (getDeviceHeightPx(requireContext()) - 115.px(
                        requireContext()
                    )).toFloat() / getDeviceHeightPx(requireContext())
                ) {
                    binding.clSearch.apply {
                        visibility = View.VISIBLE
                        alpha = 1 - 1.2F * slideOffset
                    }
                } else {
                    binding.clSearch.visibility = View.GONE
                }
            }
        })
    }

    private fun initBehaviorFilter() {
        var isDragging = false

        behaviorFilter.addBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> isDragging = false
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        isDragging = false
                        searchViewModel.setStateBehaviorFilter(true)
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> isDragging = true
                    BottomSheetBehavior.STATE_SETTLING -> {}
                    BottomSheetBehavior.STATE_HIDDEN -> {}
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {}
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if (isDragging) if (slideOffset <= (getDeviceHeightPx(requireContext()) - 115.px(
                        requireContext()
                    )).toFloat() / getDeviceHeightPx(requireContext())
                ) {
                    binding.clSearch.apply {
                        visibility = View.VISIBLE
                        alpha = 1 - 1.2F * slideOffset
                    }
                } else {
                    binding.clSearch.visibility = View.GONE
                }
            }
        })
    }

    private fun observeStateBehavior() {
        searchViewModel.stateBehaviorArea.observe(viewLifecycleOwner) {
            if (!it) {
                binding.clSearch.visibility = View.VISIBLE
                behaviorArea.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
        searchViewModel.stateBehaviorFilter.observe(viewLifecycleOwner) {
            if (!it) {
                binding.clSearch.visibility = View.VISIBLE
                behaviorFilter.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }

    private fun initBehaviorState() {
        if (searchViewModel.stateBehaviorList.value!!) {
            behaviorList.state = BottomSheetBehavior.STATE_EXPANDED
            binding.apply {
                viewEmptySpace.visibility = View.VISIBLE
                clSearch.setBackgroundResource(R.drawable.bg_rect_bilbao_under_radius30)
                rlSearchAgain.visibility = View.GONE
                rlShowList.visibility = View.GONE
                rlShowMap.visibility = View.VISIBLE
            }
        } else
            behaviorList.state = BottomSheetBehavior.STATE_COLLAPSED
    }
}