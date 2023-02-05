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
import com.ssafy.campinity.data.remote.datasource.search.SearchFilterRequest
import com.ssafy.campinity.databinding.FragmentSearchFilterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFilterFragment : Fragment() {

    private lateinit var binding: FragmentSearchFilterBinding
    private lateinit var industry: Array<String>
    private lateinit var facility: Array<String>
    private lateinit var amenity: Array<String>
    private lateinit var theme: Array<String>
    private lateinit var pet: Array<String>
    private lateinit var season: Array<String>
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
        for (i in 1..4) {
            val btnId =
                resources.getIdentifier("btn_industry_$i", "id", requireContext().packageName)
            industryBtnList.add(parent.findViewById(btnId) as TextView)
        }

        for (i in 1..9) {
            val btnId =
                resources.getIdentifier("btn_facility_$i", "id", requireContext().packageName)
            facilityBtnList.add(parent.findViewById(btnId) as TextView)
        }

        for (i in 1..11) {
            val btnId =
                resources.getIdentifier("btn_amenity_$i", "id", requireContext().packageName)
            amenityBtnList.add(parent.findViewById(btnId) as TextView)
        }

        for (i in 1..12) {
            val btnId = resources.getIdentifier("btn_theme_$i", "id", requireContext().packageName)
            themeBtnList.add(parent.findViewById(btnId) as TextView)
        }

        for (i in 1..3) {
            val btnId = resources.getIdentifier("btn_pet_$i", "id", requireContext().packageName)
            petBtnList.add(parent.findViewById(btnId) as TextView)
        }

        for (i in 1..4) {
            val btnId = resources.getIdentifier("btn_season_$i", "id", requireContext().packageName)
            seasonBtnList.add(parent.findViewById(btnId) as TextView)
        }
    }

    private fun initBtnListener() {
        industry = resources.getStringArray(R.array.content_campsite_industry)
        facility = resources.getStringArray(R.array.content_campsite_facility)
        amenity = resources.getStringArray(R.array.content_campsite_amenity)
        theme = resources.getStringArray(R.array.content_campsite_theme)
        pet = resources.getStringArray(R.array.content_campsite_pet)
        season = resources.getStringArray(R.array.content_campsite_season)

        for (i in industry.indices) {
            industryBtnList[i].let { btn ->
                btn.text = industry[i]
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

        for (i in facility.indices) {
            facilityBtnList[i].let { btn ->
                btn.text = facility[i]
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

        for (i in amenity.indices) {
            amenityBtnList[i].let { btn ->
                btn.text = amenity[i]
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

        for (i in theme.indices) {
            themeBtnList[i].let { btn ->
                btn.text = theme[i]
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

        for (i in pet.indices) {
            petBtnList[i].let { btn ->
                btn.text = pet[i]
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

        for (i in season.indices) {
            seasonBtnList[i].let { btn ->
                btn.text = season[i]
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
        for (i in industry.indices) {
            industryBtnList[i].let { btn ->
                btn.isSelected = false
                btn.setBackgroundResource(R.drawable.bg_rect_white_grey_alpha30_radius10_stroke1)
                selectedIndustry.remove(i + 1)
            }
        }

        for (i in facility.indices) {
            facilityBtnList[i].let { btn ->
                btn.isSelected = false
                btn.setBackgroundResource(R.drawable.bg_rect_white_grey_alpha30_radius10_stroke1)
                selectedFacility.remove(i + 1)
            }
        }

        for (i in amenity.indices) {
            amenityBtnList[i].let { btn ->
                btn.isSelected = false
                btn.setBackgroundResource(R.drawable.bg_rect_white_grey_alpha30_radius10_stroke1)
                selectedAmenity.remove(i + 1)
            }
        }

        for (i in theme.indices) {
            themeBtnList[i].let { btn ->
                btn.isSelected = false
                btn.setBackgroundResource(R.drawable.bg_rect_white_grey_alpha30_radius10_stroke1)
                selectedTheme.remove(i + 1)
            }
        }

        for (i in pet.indices) {
            petBtnList[i].let { btn ->
                btn.isSelected = false
                btn.setBackgroundResource(R.drawable.bg_rect_white_grey_alpha30_radius10_stroke1)
                selectedPet.remove(btn.text.toString())
            }
        }

        for (i in season.indices) {
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
                this.filter = SearchFilterRequest(
                    industry = selectedIndustry.toFilter(),
                    fclty = selectedFacility.toFilter(),
                    amenity = selectedAmenity.toFilter(),
                    theme = selectedTheme.toFilter(),
                    allowAnimal = selectedPet.toFilter(),
                    openSeason = selectedSeason.toFilter()
                )

                this.getCampsitesByFiltering(this.filter)

                resetBtn()
                this.setStateBehaviorFilter(false)
            }
        }
    }

    private fun ArrayList<*>.toFilter(): String {
        val stringBuilder = StringBuilder()

        this.forEachIndexed { index, d ->
            if (index == 0) {
                stringBuilder.append(d)
            } else {
                stringBuilder.append(" $d")
            }
        }

        return stringBuilder.toString()
    }
}