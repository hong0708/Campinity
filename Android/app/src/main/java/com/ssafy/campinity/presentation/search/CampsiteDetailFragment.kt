package com.ssafy.campinity.presentation.search

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.ssafy.campinity.R
import com.ssafy.campinity.common.util.LinearItemDecoration
import com.ssafy.campinity.common.util.getDeviceWidthPx
import com.ssafy.campinity.common.util.px
import com.ssafy.campinity.common.util.toString
import com.ssafy.campinity.databinding.FragmentCampsiteDetailBinding
import com.ssafy.campinity.domain.entity.search.CampsiteDetailInfo
import com.ssafy.campinity.domain.entity.search.FacilityAndLeisureItem
import com.ssafy.campinity.presentation.base.BaseFragment

class CampsiteDetailFragment :
    BaseFragment<FragmentCampsiteDetailBinding>(R.layout.fragment_campsite_detail) {

    private lateinit var contentTheme: Array<String>
    private lateinit var contentFacility: Array<String>
    private lateinit var contentAmenity: Array<String>
    private val searchViewModel by activityViewModels<SearchViewModel>()

    override fun initView() {
        initStringArray()
        initFragment()
        initListener()
    }

    private fun initStringArray() {
        contentTheme = resources.getStringArray(R.array.content_campsite_theme)
        contentFacility = resources.getStringArray(R.array.content_campsite_facility)
        contentAmenity = resources.getStringArray(R.array.content_campsite_amenity)
    }

    private fun initFragment() {
        binding.clCampsiteReview.visibility = View.GONE
        binding.btnGoBack.setOnClickListener {
            popBackStack()
        }

        searchViewModel.campsiteData.value.let { campsiteDetailInfo ->
            initViewPager()
            binding.tvCampsiteIndustry.text = campsiteDetailInfo?.industries?.toString(" | ")
            binding.tvCampsiteName.text =
                if (campsiteDetailInfo != null && campsiteDetailInfo.campsiteName.isNotEmpty())
                    campsiteDetailInfo.campsiteName
                else
                    "이름 미등록 캠핑장"
            binding.tvCampsiteShortContent.text = campsiteDetailInfo?.lineIntro
            binding.tvCampsiteLocation.text = campsiteDetailInfo?.address
            binding.tvCampsiteCall.text =
                if (campsiteDetailInfo != null && campsiteDetailInfo.phoneNumber.isNotEmpty())
                    campsiteDetailInfo.phoneNumber
                else
                    "미등록"
            binding.tvContentCampsiteOpenSeason.text =
                if (campsiteDetailInfo != null && campsiteDetailInfo.openSeasons.isNotEmpty()) {
                    campsiteDetailInfo.openSeasons.toString(" | ")
                } else {
                    binding.tvTitleCampsiteOpenSeason.visibility = View.GONE
                    ""
                }
            binding.tvContentCampsitePet.text =
                if (campsiteDetailInfo != null && campsiteDetailInfo.allowAnimal.isNotEmpty()) {
                    campsiteDetailInfo.allowAnimal
                } else {
                    binding.tvTitleCampsitePet.visibility = View.GONE
                    ""
                }
            binding.tvContentCampsiteHowToReserve.text =
                if (campsiteDetailInfo != null && campsiteDetailInfo.reserveType.isNotEmpty()) {
                    campsiteDetailInfo.reserveType
                } else {
                    binding.tvTitleCampsiteHowToReserve.visibility = View.GONE
                    ""
                }
            binding.tvContentCampsiteHomepageUrl.text =
                if (campsiteDetailInfo != null && campsiteDetailInfo.homepage.isNotEmpty()) {
                    campsiteDetailInfo.homepage
                } else {
                    binding.tvTitleCampsiteHomepageUrl.visibility = View.GONE
                    ""
                }

            val viewTreeObserver = binding.tvCampsiteLongContent.viewTreeObserver
            viewTreeObserver.addOnGlobalLayoutListener {
                if (campsiteDetailInfo == null || campsiteDetailInfo.intro.isEmpty()) {
                    binding.clCampsiteLongContent.visibility = View.GONE
                    binding.tvCampsiteLongContent.text = ""
                } else {
                    binding.tvCampsiteLongContent.apply {
                        text = campsiteDetailInfo.intro
                        if (this.layout.getEllipsisCount(lineCount - 1) > 0) {
                            binding.tvShowMore.setOnClickListener {
                                it.visibility = View.GONE
                                it.isClickable = false
                                ellipsize = null
                                maxLines = Int.MAX_VALUE
                            }
                        } else {
                            binding.tvShowMore.visibility = View.GONE
                            binding.tvShowMore.isClickable = false
                        }
                    }
                }
            }

            mapFacilityAndLeisureList(campsiteDetailInfo!!)
        }
    }

    private fun mapFacilityAndLeisureList(campsiteDetailInfo: CampsiteDetailInfo) {
        val facilityAndLeisureList: ArrayList<FacilityAndLeisureItem> = arrayListOf()

        campsiteDetailInfo.caravanFacilities.forEach {
            facilityAndLeisureList.add(
                FacilityAndLeisureItem(
                    resources.getIdentifier(
                        "ic_campsite_facility_$it",
                        "drawable",
                        requireContext().packageName
                    ), contentFacility[it - 1]
                )
            )
        }

        campsiteDetailInfo.glampingFacilities.forEach {
            if (!facilityAndLeisureList.contains(
                    FacilityAndLeisureItem(
                        resources.getIdentifier(
                            "ic_campsite_facility_$it",
                            "drawable",
                            requireContext().packageName
                        ), contentFacility[it - 1]
                    )
                )
            ) facilityAndLeisureList.add(
                FacilityAndLeisureItem(
                    resources.getIdentifier(
                        "ic_campsite_facility_$it",
                        "drawable",
                        requireContext().packageName
                    ), contentFacility[it - 1]
                )
            )
        }

        campsiteDetailInfo.amenities.forEach {
            facilityAndLeisureList.add(
                FacilityAndLeisureItem(
                    resources.getIdentifier(
                        "ic_campsite_amenity_$it",
                        "drawable",
                        requireContext().packageName
                    ), contentAmenity[it - 1]
                )
            )
        }

        campsiteDetailInfo.themes.forEach {
            facilityAndLeisureList.add(
                FacilityAndLeisureItem(
                    resources.getIdentifier(
                        "ic_campsite_theme_$it", "drawable", requireContext().packageName
                    ), contentTheme[it - 1]
                )
            )
        }

        if (facilityAndLeisureList.contains(
                FacilityAndLeisureItem(
                    resources.getIdentifier(
                        "ic_campsite_facility_5", "drawable", requireContext().packageName
                    ), contentFacility[4]
                )
            )
        ) facilityAndLeisureList.remove(
            FacilityAndLeisureItem(
                resources.getIdentifier(
                    "ic_campsite_amenity_8", "drawable", requireContext().packageName
                ), contentAmenity[7]
            )
        )

        initRecyclerView(facilityAndLeisureList)
    }

    private fun initViewPager() {
        binding.vpCampsiteImage.apply {
            adapter =
                CampsiteDetailImageAdapter(searchViewModel.campsiteData.value?.images ?: listOf())
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }

        binding.ivIndicator.apply {
            val count =
                if (searchViewModel.campsiteData.value?.images != null) searchViewModel.campsiteData.value?.images!!.size else 1
            setSliderHeight(5.px(requireContext()).toFloat())
            setSliderGap(0F)
            setupWithViewPager(binding.vpCampsiteImage)
            setSliderWidth(getDeviceWidthPx(requireContext()).toFloat() / count)
        }
    }

    private fun initRecyclerView(facilityAndLeisure: List<FacilityAndLeisureItem>) {
        if (facilityAndLeisure.isNotEmpty())
            binding.rvCampsiteFacilityAndLeisure.apply {
                layoutManager = LinearLayoutManager(
                    requireContext(), LinearLayoutManager.HORIZONTAL, false
                )
                adapter = CampsiteFacilityAndLeisureAdapter(facilityAndLeisure)

                addItemDecoration(LinearItemDecoration(context, RecyclerView.HORIZONTAL, 20))
            }
        else
            binding.clCampsiteAmenity.visibility = View.GONE
    }

    private fun initListener() {
        binding.btnPostbox.setOnClickListener {
            navigate(
                CampsiteDetailFragmentDirections.actionCampsiteDetailFragmentToSearchPostboxFragment()
            )
        }
    }
}