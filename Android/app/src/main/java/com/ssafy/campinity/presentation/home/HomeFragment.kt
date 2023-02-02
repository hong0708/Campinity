package com.ssafy.campinity.presentation.home

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentHomeBinding
import com.ssafy.campinity.domain.entity.home.HomeCampingSite
import com.ssafy.campinity.domain.entity.home.HomeCollection
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeViewModel by viewModels<HomeViewModel>()
    private val homeBannerAdapter by lazy { HomeBannerAdapter(this::getCurationDetail) }
    private val handler = Handler(Looper.getMainLooper()) {
        setPage()
        true
    }
    private var currentPage = 0

    override fun initView() {
        initListener()
        initCollection()
        initBanner()
        initCampingSite()
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.getHomeBanners()
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
        val child = binding.vpBannerHome.getChildAt(0)
        val thread = Thread(PagerRunnable())

        binding.vpBannerHome.adapter = homeBannerAdapter
        binding.vpBannerHome.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        homeViewModel.homeBanners.observe(viewLifecycleOwner) { response ->
            response?.let { homeBannerAdapter.addHomeBanner(it) }
        }
        homeViewModel.getHomeBanners()

        (child as? RecyclerView)?.overScrollMode = View.OVER_SCROLL_NEVER
        thread.start()
    }

    private fun getCurationDetail(curationId: String) {
        navigate(
            HomeFragmentDirections.actionHomeFragmentToCurationDetailFragment(curationId)
        )
    }

    private fun setPage(){
        if (currentPage == 3) currentPage = 0
        binding.vpBannerHome.setCurrentItem(currentPage, true)
        currentPage += 1
    }

    inner class PagerRunnable:Runnable{
        override fun run() {
            while(true){
                try {
                    Thread.sleep(3000)
                    handler.sendEmptyMessage(0)
                } catch (e : InterruptedException){
                    Log.e("interrupt", e.message.toString())
                }
            }
        }
    }
}