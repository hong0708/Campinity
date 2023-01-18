package com.ssafy.campinity.presentation.community

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.ActivityCommunityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommunityBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_community) as NavHostFragment
        navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.navigation_community)
        navController.graph = navGraph
    }
}