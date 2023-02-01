package com.ssafy.campinity.presentation.home

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentHomeBinding
import com.ssafy.campinity.domain.entity.home.HomeCampingSite
import com.ssafy.campinity.domain.entity.home.HomeCollection
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlin.math.ceil

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeViewModel by viewModels<HomeViewModel>()
    private val homeBannerAdapter by lazy { HomeBannerAdapter(this::getCurationDetail) }
    private var bannerPosition = 0
    lateinit var job: Job

    override fun initView() {
        initListener()
        initCollection()
        initBanner()
        initCampingSite()
    }

    private fun initListener() {
        binding.apply {
            tvCollectionMore.setOnClickListener {
                navigate(HomeFragmentDirections.actionHomeFragmentToCollectionFragment())
            }
            clSearch.setOnClickListener {
                navigate(HomeFragmentDirections.actionHomeFragmentToSearchActivity())
            }
            clCommunity.setOnClickListener {
                navigate(HomeFragmentDirections.actionHomeFragmentToCommunityActivity())
            }
            tvBannerMore.setOnClickListener {
                navigate(HomeFragmentDirections.actionHomeFragmentToCurationFragment())
            }
        }
    }

    private fun initCampingSite() {
        val list: ArrayList<HomeCampingSite> = ArrayList<HomeCampingSite>().let {
            it.apply {
                add(
                    HomeCampingSite(
                        R.drawable.bg_home_banner, "비니비니 글램핑", "서울 특별시, 대한민국"
                    )
                )
                add(
                    HomeCampingSite(
                        R.drawable.bg_home_banner, "비니비니 글램핑", "서울 특별시, 대한민국"
                    )
                )
                add(
                    HomeCampingSite(
                        R.drawable.bg_home_banner, "비니비니 글램핑", "서울 특별시, 대한민국"
                    )
                )
            }
        }
        binding.rvPopularCampingSite.adapter = HomeCampingSiteAdapter(list)
        binding.rvScoreCampingSite.adapter = HomeCampingSiteAdapter(list)
    }

    private fun initCollection() {
        val list: ArrayList<HomeCollection> = ArrayList<HomeCollection>().let {
            it.apply {
                add(
                    HomeCollection(
                        R.drawable.bg_home_banner, "싸피캠핑장", "2023/01/08"
                    )
                )
                add(
                    HomeCollection(
                        R.drawable.bg_home_banner, "싸피캠핑장", "2023/01/08"
                    )
                )
                add(
                    HomeCollection(
                        R.drawable.bg_home_banner, "싸피캠핑장", "2023/01/08"
                    )
                )
            }
        }
        binding.rvCollectionHome.adapter = HomeCollectionAdapter(list)
    }

    private fun initBanner() {
        binding.vpBannerHome.adapter = homeBannerAdapter
        binding.vpBannerHome.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        homeViewModel.homeBanners.observe(viewLifecycleOwner) { response ->
            Log.d("initBanner", "initBanner: $response")
            response?.let { homeBannerAdapter.setHomeBanner(it) }
        }
        homeViewModel.getHomeBanners()

        binding.vpBannerHome.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                bannerPosition = position
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                when (state) {
                    ViewPager2.SCROLL_STATE_IDLE -> {
                        if (!job.isActive) scrollJobCreate()
                    }

                    ViewPager2.SCROLL_STATE_DRAGGING -> job.cancel()

                    ViewPager2.SCROLL_STATE_SETTLING -> {}
                }
            }
        })

        bannerPosition = Int.MAX_VALUE / 2 - ceil(3.toDouble() / 2).toInt()
        binding.vpBannerHome.setCurrentItem(bannerPosition, false)
    }

    private fun getCurationDetail(curationId: String) {
        navigate(
            HomeFragmentDirections.actionHomeFragmentToCurationDetailFragment(curationId)
        )
    }

    fun scrollJobCreate() {
        job = lifecycleScope.launchWhenResumed {
            delay(2200)
            binding.vpBannerHome.setCurrentItem(++bannerPosition, true)
        }
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.getHomeBanners()
        scrollJobCreate()
    }

    override fun onPause() {
        super.onPause()
        job.cancel()
    }
}