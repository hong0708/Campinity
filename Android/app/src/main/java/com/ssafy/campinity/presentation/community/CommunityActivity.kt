package com.ssafy.campinity.presentation.community

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.ActivityCommunityBinding
import dagger.hilt.android.AndroidEntryPoint
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.ssafy.campinity.common.util.showToastMessage
import com.ssafy.campinity.presentation.community.campsite.LocationService

@AndroidEntryPoint
class CommunityActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommunityBinding
    private lateinit var navController: NavController

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (checkLocationService()) {
            // GPS가 켜져있을 경우
            checkPermission()
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_community) as NavHostFragment
            navController = navHostFragment.navController
            val navGraph = navController.navInflater.inflate(R.navigation.navigation_community)
            navController.graph = navGraph

        } else {
            // GPS가 꺼져있을 경우
            showToastMessage("GPS를 켜주세요")
        }
    }

    // GPS가 켜져있는지 확인
    private fun checkLocationService(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun checkPermission() {
        TedPermission.create()
            .setPermissionListener(object : PermissionListener {
                override fun onPermissionGranted() {
                    Log.d("permission11", "onPermissionGranted: ")
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    onBackPressed()
                }
            })
            /*.setRationaleMessage("위치 정보 제공이 필요한 서비스입니다.")*/
            .setDeniedMessage("권한을 허용해주세요. [권한] > [위치] > [항상 허용]")
            .setDeniedCloseButtonText("닫기")
            .setGotoSettingButtonText("설정")
            /*.setRationaleTitle("Campinity")*/
            .setPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ).check()
    }

    fun startLocationBackground() {
        Intent(applicationContext, LocationService::class.java).apply {
            action = LocationService.ACTION_START
            startService(this)
        }
    }

    fun stopLocationBackground() {
        Intent(applicationContext, LocationService::class.java).apply {
            action = LocationService.ACTION_STOP
            startService(this)
        }
    }
}