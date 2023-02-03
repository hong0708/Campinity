package com.ssafy.campinity.presentation.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.ssafy.campinity.R
import com.ssafy.campinity.data.local.AreaDataBase
import com.ssafy.campinity.databinding.ActivitySearchBinding
import com.ssafy.campinity.domain.entity.search.AreaEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var navController: NavController
    private lateinit var areaDataBase: AreaDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val graphInflater = navHostFragment.navController.navInflater
        val navGraph = graphInflater.inflate(R.navigation.navigation_search)

        navController = navHostFragment.navController
        navController.graph = navGraph

        setAreaDataBase()
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
            val sidoName = stringArray[0]

            stringArray.forEachIndexed { index, s ->
                if (index != 0) {
                    lifecycleScope.launch {
                        areaDataBase.areaDao().insert(
                            AreaEntity(
                                sidoName,
                                s.split("(")[0],
                                s.split("(")[1].split(")")[0].toInt()
                            )
                        )
                    }
                }
            }
        }
    }
}