package com.ssafy.campinity.presentation.onboarding

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.ssafy.campinity.ApplicationClass
import com.ssafy.campinity.R
import com.ssafy.campinity.common.util.Permission
import com.ssafy.campinity.databinding.FragmentPermissionBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PermissionFragment : BaseFragment<FragmentPermissionBinding>(R.layout.fragment_permission) {

    private val onBoardingViewModel by viewModels<OnBoardingViewModel>()

    private val actionTrue: NavDirections =
        PermissionFragmentDirections.actionPermissionFragmentToHomeFragment()
    private val actionFalse: NavDirections =
        PermissionFragmentDirections.actionPermissionFragmentToOnboardingFragment()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun initView() {
        initObserver()
        initPermission()
        setButtonClickListener()
        //checkPermission()

        binding.apply {
            //setButtonView()

            /*if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                ivCheckLocation.setImageResource(R.drawable.ic_permission_check_true)
            } else {
                ivCheckLocation.setImageResource(R.drawable.ic_permission_check_false)
            }

            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                ivCheckPhoto.setImageResource(R.drawable.ic_permission_check_true)
            } else {
                ivCheckPhoto.setImageResource(R.drawable.ic_permission_check_false)
            }*/
        }

        binding.apply {
            btnConfirm.setOnClickListener {

                if (ApplicationClass.preferences.isLoggedIn) {
                    navigate(actionTrue)
                } else {
                    navigate(actionFalse)
                }

            }
        }
    }

    private fun initObserver() {
        onBoardingViewModel.locationPermission.observe(viewLifecycleOwner) {
            if (it) {
                binding.ivCheckLocation.setImageResource(R.drawable.ic_permission_check_true)
            } else {
                binding.ivCheckLocation.setImageResource(R.drawable.ic_permission_check_false)
            }
        }

        onBoardingViewModel.photoPermission.observe(viewLifecycleOwner) {
            if (it) {
                binding.ivCheckPhoto.setImageResource(R.drawable.ic_permission_check_true)
            } else {
                binding.ivCheckPhoto.setImageResource(R.drawable.ic_permission_check_false)
            }
        }
    }

    private fun initPermission() {
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            onBoardingViewModel.checkLocationPermission(true)
        } else {
            onBoardingViewModel.checkLocationPermission(true)
        }

        if (checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            onBoardingViewModel.checkPhotoPermission(true)
        } else {
            onBoardingViewModel.checkPhotoPermission(false)
        }
    }

    private fun setButtonView() {
        if (
            checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            /*&& checkPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION)*/
            && checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
        ) {
            binding.btnConfirm.setBackgroundResource(R.drawable.bg_rect_bilbao_radius10)
        } else {
            binding.btnConfirm.setBackgroundResource(R.drawable.bg_rect_white_smoke_radius10)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun setButtonClickListener() {
        binding.apply {
            btnConfirm.setOnClickListener {
                if (
                    checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    /*&& checkPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION)*/
                    && checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                ) {
                    if (ApplicationClass.preferences.isLoggedIn) {
                        navigate(actionTrue)
                    } else {
                        navigate(actionFalse)
                    }
                } else {
                    showToast("권한을 먼저 확인해 주세요")
                }
            }

            ivCheckPhoto.setOnClickListener {
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    /*ivCheckPhoto.setImageResource(R.drawable.ic_permission_check_true)*/
                    onBoardingViewModel.checkPhotoPermission(true)
                    //setButtonView()
                } else {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ),
                        Permission.REQUEST_READ_STORAGE_PERMISSION
                    )
                }
            }

            ivCheckLocation.setOnClickListener {
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    /*ivCheckLocation.setImageResource(R.drawable.ic_permission_check_true)*/
                    onBoardingViewModel.checkLocationPermission(true)
                    //setButtonView()
                } else {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ),
                        Permission.ACCESS_FINE_LOCATION
                    )
                }
            }
        }
    }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun checkPermission1() {
        TedPermission.create()
            .setPermissionListener(object : PermissionListener {
                override fun onPermissionGranted() {
                    Log.d("permission11", "onPermissionGranted: ")
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    showToast("권한을 허가해주세요.")
                    Log.d("permission11", "onPermissionDenied: ${deniedPermissions.toString()}")
                }
            })
            /*.setRationaleMessage("위치 정보 제공이 필요한 서비스입니다.")*/
            .setDeniedMessage("권한을 허용해주세요. [권한] > [위치]")
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
}