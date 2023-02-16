package com.ssafy.campinity.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ssafy.campinity.R
import com.ssafy.campinity.common.util.showToastMessage
import com.ssafy.campinity.common.util.toString
import com.ssafy.campinity.data.remote.datasource.search.SearchFilterClusteringRequest
import com.ssafy.campinity.data.remote.datasource.search.SearchFilterRequest
import com.ssafy.campinity.databinding.FragmentSearchFilterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFilterFragment : Fragment() {

    private lateinit var binding: FragmentSearchFilterBinding
    private val industryBtnList: ArrayList<TextView> = arrayListOf()
    private val facilityBtnList: ArrayList<TextView> = arrayListOf()
    private val amenityBtnList: ArrayList<TextView> = arrayListOf()
    private val themeBtnList: ArrayList<TextView> = arrayListOf()
    private val petBtnList: ArrayList<TextView> = arrayListOf()
    private val seasonBtnList: ArrayList<TextView> = arrayListOf()
    private val searchViewModel by activityViewModels<SearchViewModel>()
    private var selectedIndustry = arrayListOf<Int>()
    private var selectedFacility = arrayListOf<Int>()
    private var selectedAmenity = arrayListOf<Int>()
    private var selectedTheme = arrayListOf<Int>()
    private var selectedPet = arrayListOf<String>()
    private var selectedSeason = arrayListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val parent = inflater.inflate(R.layout.fragment_search_filter, container, false)

        initBtnList(parent)
        return parent
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchFilterBinding.bind(view)
        initBtnListener()
        initResetBtnListener()
        initSubmitListener()
    }

    private fun initBtnList(parent: View) {
        for (i in searchViewModel.industry.indices) {
            val btnId =
                resources.getIdentifier("btn_industry_${i + 1}", "id", requireContext().packageName)
            (parent.findViewById(btnId) as TextView).apply {
                text = searchViewModel.industry[i]
                industryBtnList.add(this)
            }
        }

        for (i in searchViewModel.facility.indices) {
            val btnId =
                resources.getIdentifier("btn_facility_${i + 1}", "id", requireContext().packageName)
            (parent.findViewById(btnId) as TextView).apply {
                text = searchViewModel.facility[i]
                facilityBtnList.add(this)
            }
        }

        for (i in searchViewModel.amenity.indices) {
            val btnId =
                resources.getIdentifier("btn_amenity_${i + 1}", "id", requireContext().packageName)
            (parent.findViewById(btnId) as TextView).apply {
                text = searchViewModel.amenity[i]
                amenityBtnList.add(this)
            }
        }

        for (i in searchViewModel.theme.indices) {
            val btnId =
                resources.getIdentifier("btn_theme_${i + 1}", "id", requireContext().packageName)
            (parent.findViewById(btnId) as TextView).apply {
                text = searchViewModel.theme[i]
                themeBtnList.add(this)
            }
        }

        for (i in searchViewModel.pet.indices) {
            val btnId =
                resources.getIdentifier("btn_pet_${i + 1}", "id", requireContext().packageName)
            (parent.findViewById(btnId) as TextView).apply {
                text = searchViewModel.pet[i]
                petBtnList.add(this)
            }
        }

        for (i in searchViewModel.season.indices) {
            val btnId =
                resources.getIdentifier("btn_season_${i + 1}", "id", requireContext().packageName)
            (parent.findViewById(btnId) as TextView).apply {
                text = searchViewModel.season[i]
                seasonBtnList.add(this)
            }
        }
    }

    private fun initBtnListener() {
        for (i in searchViewModel.industry.indices) {
            industryBtnList[i].let { btn ->
                btn.setOnClickListener {
                    if (btn.isSelected) {
                        btn.isSelected = false
                        btn.setBackgroundResource(R.drawable.bg_rect_white_grey_alpha30_radius10_stroke1)
                        selectedIndustry.remove(i + 1)
                    } else {
                        btn.isSelected = true
                        btn.setBackgroundResource(R.drawable.bg_rect_primrose_grey_alpha30_radius10_stroke1)
                        selectedIndustry.add(i + 1)
                    }
                    toggleSubmitButton()
                }
            }
        }

        for (i in searchViewModel.facility.indices) {
            facilityBtnList[i].let { btn ->
                btn.setOnClickListener {
                    if (btn.isSelected) {
                        btn.isSelected = false
                        btn.setBackgroundResource(R.drawable.bg_rect_white_grey_alpha30_radius10_stroke1)
                        selectedFacility.remove(i + 1)
                    } else {
                        btn.isSelected = true
                        btn.setBackgroundResource(R.drawable.bg_rect_primrose_grey_alpha30_radius10_stroke1)
                        selectedFacility.add(i + 1)
                    }
                    toggleSubmitButton()
                }
            }
        }

        for (i in searchViewModel.amenity.indices) {
            amenityBtnList[i].let { btn ->
                btn.setOnClickListener {
                    if (btn.isSelected) {
                        btn.isSelected = false
                        btn.setBackgroundResource(R.drawable.bg_rect_white_grey_alpha30_radius10_stroke1)
                        selectedAmenity.remove(i + 1)
                    } else {
                        btn.isSelected = true
                        btn.setBackgroundResource(R.drawable.bg_rect_primrose_grey_alpha30_radius10_stroke1)
                        selectedAmenity.add(i + 1)
                    }
                    toggleSubmitButton()
                }
            }
        }

        for (i in searchViewModel.theme.indices) {
            themeBtnList[i].let { btn ->
                btn.setOnClickListener {
                    if (btn.isSelected) {
                        btn.isSelected = false
                        btn.setBackgroundResource(R.drawable.bg_rect_white_grey_alpha30_radius10_stroke1)
                        selectedTheme.remove(i + 1)
                    } else {
                        btn.isSelected = true
                        btn.setBackgroundResource(R.drawable.bg_rect_primrose_grey_alpha30_radius10_stroke1)
                        selectedTheme.add(i + 1)
                    }
                    toggleSubmitButton()
                }
            }
        }

        for (i in searchViewModel.pet.indices) {
            petBtnList[i].let { btn ->
                btn.setOnClickListener {
                    if (btn.isSelected) {
                        btn.isSelected = false
                        btn.setBackgroundResource(R.drawable.bg_rect_white_grey_alpha30_radius10_stroke1)
                        selectedPet.remove(btn.text.toString())
                    } else {
                        btn.isSelected = true
                        btn.setBackgroundResource(R.drawable.bg_rect_primrose_grey_alpha30_radius10_stroke1)
                        selectedPet.add(btn.text.toString())
                    }
                    toggleSubmitButton()
                }
            }
        }

        for (i in searchViewModel.season.indices) {
            seasonBtnList[i].let { btn ->
                btn.setOnClickListener {
                    if (btn.isSelected) {
                        btn.isSelected = false
                        btn.setBackgroundResource(R.drawable.bg_rect_white_grey_alpha30_radius10_stroke1)
                        selectedSeason.remove(i + 1)
                    } else {
                        btn.isSelected = true
                        btn.setBackgroundResource(R.drawable.bg_rect_primrose_grey_alpha30_radius10_stroke1)
                        selectedSeason.add(i + 1)
                    }
                    toggleSubmitButton()
                }
            }
        }
    }

    private fun resetBtn() {
        for (i in searchViewModel.industry.indices) {
            industryBtnList[i].let { btn ->
                btn.isSelected = false
                btn.setBackgroundResource(R.drawable.bg_rect_white_grey_alpha30_radius10_stroke1)
                selectedIndustry.remove(i + 1)
            }
        }

        for (i in searchViewModel.facility.indices) {
            facilityBtnList[i].let { btn ->
                btn.isSelected = false
                btn.setBackgroundResource(R.drawable.bg_rect_white_grey_alpha30_radius10_stroke1)
                selectedFacility.remove(i + 1)
            }
        }

        for (i in searchViewModel.amenity.indices) {
            amenityBtnList[i].let { btn ->
                btn.isSelected = false
                btn.setBackgroundResource(R.drawable.bg_rect_white_grey_alpha30_radius10_stroke1)
                selectedAmenity.remove(i + 1)
            }
        }

        for (i in searchViewModel.theme.indices) {
            themeBtnList[i].let { btn ->
                btn.isSelected = false
                btn.setBackgroundResource(R.drawable.bg_rect_white_grey_alpha30_radius10_stroke1)
                selectedTheme.remove(i + 1)
            }
        }

        for (i in searchViewModel.pet.indices) {
            petBtnList[i].let { btn ->
                btn.isSelected = false
                btn.setBackgroundResource(R.drawable.bg_rect_white_grey_alpha30_radius10_stroke1)
                selectedPet.remove(btn.text.toString())
            }
        }

        for (i in searchViewModel.season.indices) {
            seasonBtnList[i].let { btn ->
                btn.isSelected = false
                btn.setBackgroundResource(R.drawable.bg_rect_white_grey_alpha30_radius10_stroke1)
                selectedSeason.remove(i + 1)
            }
        }

        toggleSubmitButton()
    }

    private fun initResetBtnListener() {
        binding.llReset.setOnClickListener {
            resetBtn()
        }
    }

    private fun toggleSubmitButton() {
        val countSelected =
            selectedIndustry.size + selectedFacility.size + selectedAmenity.size + selectedTheme.size + selectedPet.size + selectedSeason.size
        if (countSelected == 0) {
            binding.btnSubmit.let {
                it.isClickable = false
                it.setBackgroundResource(R.drawable.bg_rect_white_smoke_radius10)
                it.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            }
        } else {
            binding.btnSubmit.let {
                it.isClickable = true
                it.setBackgroundResource(R.drawable.bg_rect_bilbao_radius10)
                it.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            }
        }
    }

    private fun initSubmitListener() {
        binding.btnSubmit.setOnClickListener {
            with(searchViewModel) {
                if (selectedGugun == "")
                    requireContext().showToastMessage("지역을 먼저 선택해주세요.")
                else {
                    filter = SearchFilterRequest(
                        doName = selectedSido,
                        sigunguName = selectedGugun,
                        industry = selectedIndustry.toString(" "),
                        fclty = selectedFacility.toString(" "),
                        amenity = selectedAmenity.toString(" "),
                        theme = selectedTheme.toString(" "),
                        allowAnimal = selectedPet.toString(" "),
                        openSeason = selectedSeason.toString(" "),
                        paging = 1
                    )
                    getCampsitesByFiltering(this.filter)


                    clusteringFilter = SearchFilterClusteringRequest(
                        industry = selectedIndustry.toString(" "),
                        fclty = selectedFacility.toString(" "),
                        amenity = selectedAmenity.toString(" "),
                        theme = selectedTheme.toString(" "),
                        allowAnimal = selectedPet.toString(" "),
                        openSeason = selectedSeason.toString(" ")
                    )
                    getCampsitesSiGunGu(clusteringFilter)
                    getCampsitesDo(clusteringFilter)

                    resetBtn()
                    setStateBehaviorFilter(false)
                }
            }
        }
    }
}