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
@RequiresApi(Build.VERSION_CODES.Q)
class CommunityActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommunityBinding
    private lateinit var navController: NavController

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
          /*  if (*//*requestPermissions()*//*

            ) {
                val navHostFragment =
                    supportFragmentManager.findFragmentById(R.id.nav_community) as NavHostFragment
                navController = navHostFragment.navController
                val navGraph = navController.navInflater.inflate(R.navigation.navigation_community)
                navController.graph = navGraph
            } else {
                showToastMessage("위치 권한을 설정해주셔야 합니다.")
            }
*/
            /* val navHostFragment =
                 supportFragmentManager.findFragmentById(R.id.nav_community) as NavHostFragment
             navController = navHostFragment.navController
             val navGraph = navController.navInflater.inflate(R.navigation.navigation_community)
             navController.graph = navGraph*/

            /*if (permissionCheck()) {
                val navHostFragment =
                    supportFragmentManager.findFragmentById(R.id.nav_community) as NavHostFragment
                navController = navHostFragment.navController
                val navGraph = navController.navInflater.inflate(R.navigation.navigation_community)
                navController.graph = navGraph
            } else {
                permissionCheck()
            }*/
        } else {
            // GPS가 꺼져있을 경우
            showToastMessage("GPS를 켜주세요")
        }
    }

    /*// 위치 권한 확인
    private fun permissionCheck(): Boolean {
        val preference = getPreferences(MODE_PRIVATE)

        val isFirstCheck = preference.getBoolean("isFirstPermissionCheck", true)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // 권한이 없는 상태
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                // 권한 거절 (다시 한 번 물어봄)
                val builder = AlertDialog.Builder(this)
                builder.setMessage("지도를 확인 하시려면 위치 권한을 허용해주세요.")
                builder.setPositiveButton("확인") { dialog, which ->
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        ACCESS_FINE_LOCATION
                    )
                }
                builder.setNegativeButton("취소") { dialog, which ->

                }
                builder.show()

                return false

            } else {
                if (isFirstCheck) {
                    // 최초 권한 요청
                    preference.edit().putBoolean("isFirstPermissionCheck", false).apply()
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        ACCESS_FINE_LOCATION
                    )
                    return false
                } else {
                    // 다시 묻지 않음 클릭 (앱 정보 화면으로 이동)
                    val builder = AlertDialog.Builder(this)
                    builder.setMessage("현재 위치를 확인하시려면 설정에서 위치 권한을 허용해주세요.")
                    builder.setPositiveButton("설정으로 이동") { dialog, which ->
                        val intent = Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("package:$packageName")
                        )
                        startActivity(intent)
                    }
                    builder.setNegativeButton("취소") { dialog, which ->

                    }
                    builder.show()
                    return false
                }
            }
        } else {
            // 권한이 있는 상태
            return true
        }
    }*/

    /* // 권한 요청 후 행동
     override fun onRequestPermissionsResult(
         requestCode: Int,
         permissions: Array<out String>,
         grantResults: IntArray
     ) {
         super.onRequestPermissionsResult(requestCode, permissions, grantResults)
         if (requestCode == ACCESS_FINE_LOCATION) {
             if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                 // 권한 요청 후 승인됨 (추적 시작)
                 showToastMessage("위치 권한이 승인되었습니다")
                 //startTracking()
             } else {
                 // 권한 요청 후 거절됨 (다시 요청 or 토스트)
                 showToastMessage("위치 권한이 거절되었습니다")
                 permissionCheck()
             }
         }
     }*/

    // GPS가 켜져있는지 확인
    private fun checkLocationService(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    /*private fun requestPermissions(): Boolean {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }

        val permissions: Array<String> = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        )

        ActivityCompat.requestPermissions(this, permissions, 0)
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            0 -> {
                if (grantResults.isNotEmpty()) {
                    var isAllGranted = true
                    // 요청한 권한 허용/거부 상태 한번에 체크
                    for (grant in grantResults) {
                        if (grant != PackageManager.PERMISSION_GRANTED) {
                            isAllGranted = false
                            break;
                        }
                    }

                    // 요청한 권한을 모두 허용했음.
                    if (isAllGranted) {
                        // 다음 step으로 ~
                        val navHostFragment =
                            supportFragmentManager.findFragmentById(R.id.nav_community) as NavHostFragment
                        navController = navHostFragment.navController
                        val navGraph =
                            navController.navInflater.inflate(R.navigation.navigation_community)
                        navController.graph = navGraph
                    }
                    // 허용하지 않은 권한이 있음. 필수권한/선택권한 여부에 따라서 별도 처리를 해주어야 함.
                    else {
                        if (!ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            )
                            || !ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                Manifest.permission.ACCESS_BACKGROUND_LOCATION
                            )
                        ) {
                            // 다시 묻지 않기 체크하면서 권한 거부 되었음.
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                .setData(Uri.parse("package:" + BuildConfig.APPLICATION_ID))
                            startActivity(intent)
                        } else {
                            // 접근 권한 거부하였음.
                            onDestroy()
                        }
                    }
                }
            }
        }
    }*/

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun checkPermission() {
        TedPermission.create()
            .setPermissionListener(object : PermissionListener {
                override fun onPermissionGranted() {
                    Log.d("permission11", "onPermissionGranted: ")
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    Log.d("permission11", "onPermissionDenied: ${deniedPermissions.toString()}")
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

        /*if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            //initMapView()
            if (ApplicationClass.preferences.isLoggedIn) {
                navigate(actionTrue)
            } else {
                navigate(actionFalse)
            }
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                Permission.ACCESS_FINE_LOCATION
            )
        }*/
    }

    fun startLocationBackground() {
        Intent(applicationContext, LocationService::class.java).apply {
            action = LocationService.ACTION_START
            startService(this)
            Log.d("tlqkf", "startLocationBackground: ")
        }

        //Log.d(TAG, "startLocationBackground: 동작함?")
    } // End of startLocationBackground

    fun stopLocationBackground() {
        Intent(applicationContext, LocationService::class.java).apply {
            action = LocationService.ACTION_STOP
            startService(this)
        }
    }
}