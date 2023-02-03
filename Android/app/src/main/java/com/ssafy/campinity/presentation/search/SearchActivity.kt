package com.ssafy.campinity.presentation.search

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.ActivitySearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var navController: NavController
    private val searchViewModel by viewModels<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAreaDataBase()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val graphInflater = navHostFragment.navController.navInflater
        val navGraph = graphInflater.inflate(R.navigation.navigation_search)

        navController = navHostFragment.navController
        navController.graph = navGraph
    }

    private fun setAreaDataBase() {
        val area = listOf(
            "gangwon",
            "gyeonggi",
            "gyeongsang_south",
            "gyeongsang_north",
            "gwangju",
            "daegu",
            "daejeon",
            "busan",
            "seoul",
            "sejong",
            "ulsan",
            "incheon",
            "jeonla_south",
            "jeonla_north",
            "jeju",
            "chungcheong_south",
            "chungcheong_north"
        )

        area.forEach { stringArrayName ->
            val resId = resources.getIdentifier("area_$stringArrayName", "array", packageName)
            val stringArray = resources.getStringArray(resId)

            lifecycleScope.launch {
                searchViewModel.insertAreaDataBase(stringArray)
            }
        }
    }
}