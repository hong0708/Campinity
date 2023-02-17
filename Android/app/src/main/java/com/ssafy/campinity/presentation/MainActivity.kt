package com.ssafy.campinity.presentation

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.ssafy.campinity.R
import com.ssafy.campinity.common.util.isGranted
import com.ssafy.campinity.data.local.datasource.SharedPreferences
import com.ssafy.campinity.databinding.ActivityMainBinding
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

        Log.d("onCreate", "onCreate: ${SharedPreferences(this).accessToken.toString()}")

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.permissionFragment,
                R.id.onBoardingFragment,
                R.id.homeFragment
            )
        )

        val graphInflater = navHostFragment.navController.navInflater
        val navGraph = graphInflater.inflate(R.navigation.navigation_main)

        val startDestination = when {
            isGranted(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_NOTIFICATION_POLICY
            ) -> {
                R.id.homeFragment
            }
            else -> R.id.permissionFragment
        }
        navGraph.setStartDestination(startDestination)
        navController.graph = navGraph

        if (intent.getStringExtra("state") == "no_assigned") { setDestination() }
    }

    private fun setDestination() {
        navController.navigate(R.id.homeFragment)
    }
}