package com.sssafy.campinity.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.sssafy.campinity.R
import com.sssafy.campinity.databinding.ActivityMainBinding
import com.sssafy.campinity.ApplicationClass
import com.sssafy.campinity.common.util.isGranted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.permissionFragment,
                R.id.onboardingFragment
            )
        )

        val graphInflater = navHostFragment.navController.navInflater
        val navGraph = graphInflater.inflate(R.navigation.nav_graph)

        navController = navHostFragment.navController
        val startDestination = when {
            isGranted() -> {
                R.id.permissionFragment
            }
            ApplicationClass.preferences.accessToken == null -> {
                R.id.onboardingFragment
            }
            else -> R.id.permissionFragment
        }
        navGraph.setStartDestination(startDestination)
        navController.graph = navGraph
    }
}