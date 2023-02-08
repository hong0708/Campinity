package com.ssafy.campinity.presentation.join

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.ssafy.campinity.ApplicationClass
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentJoinBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import com.ssafy.campinity.presentation.collection.FileDeleteDialogListener
import com.ssafy.campinity.presentation.collection.CollectionDeleteFileDialog
import com.ssafy.campinity.presentation.collection.CreateFileFragment
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class JoinFragment : BaseFragment<FragmentJoinBinding>(R.layout.fragment_join),
    FileDeleteDialogListener {

    private val viewModel by viewModels<JoinViewModel>()
    private val fromAlbumActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        result.data?.let {
            if (it.data != null) {
                viewModel.setProfileImg(
                    it.data as Uri,
                    File(absolutelyPath(it.data, requireContext()))
                )
            }
        }
    }

    override fun initView() {
        binding.vm = viewModel
        viewModel.requestCurrentToken()
        initListener()
        setTextWatcher()
        observeState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (viewModel.cancelSignUp()) {
            ApplicationClass.preferences.clearPreferences()
        }
    }

    private fun initListener() {
        binding.apply {
            ivProfileImage.setOnClickListener { setAlbumView() }
            etNickname.addTextChangedListener { viewModel.setNickname(it.toString()) }
            btnBack.setOnClickListener { navigate(JoinFragmentDirections.actionJoinFragmentToOnBoardingFragment()) }
            btnConfirm.setOnClickListener {
                if (viewModel.profileImgUri.value == null) {
                    viewModel.updateProfileWithoutImg(ApplicationClass.preferences.fcmToken!!)
                } else {
                    viewModel.updateProfile(ApplicationClass.preferences.fcmToken!!)
                }
            }
            btnCheckDuplication.setOnClickListener {
                if (viewModel.nickname.value == null) {
                    Toast.makeText(requireContext(), "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.checkDuplication()
                }
            }
        }
    }

    private fun observeState() {
        viewModel.isDuplicate.observe(viewLifecycleOwner) {
            when (it) {
                true -> Toast.makeText(requireContext(), "중복된 닉네임입니다.", Toast.LENGTH_SHORT).show()
                false -> Toast.makeText(requireContext(), "사용할 수 있는 닉네임입니다.", Toast.LENGTH_SHORT)
                    .show()
                else -> {}
            }
        }

        viewModel.isSuccess.observe(viewLifecycleOwner) {
            when (it) {
                true -> navigate(JoinFragmentDirections.actionJoinFragmentToCompleteJoinFragment())
                else -> {}
            }
        }
    }

    private fun setTextWatcher() {
        binding.etNickname.addTextChangedListener {
            viewModel.nickname.value = binding.etNickname.text.toString()
        }
    }

    private fun setAlbumView() {
        if (viewModel.profileImgUri.value == null) {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) -> {
                    fromAlbumActivityLauncher.launch(
                        Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        )
                    )
                }
                else -> {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        CreateFileFragment.REQUEST_READ_STORAGE_PERMISSION
                    )
                }
            }
        } else {
            val dialog = CollectionDeleteFileDialog(requireContext(), this)
            dialog.show()
        }
    }

    private fun absolutelyPath(path: Uri?, context: Context): String {
        val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        val c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
        val index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()
        val result = c?.getString(index!!)
        c?.close()
        return result!!
    }

    override fun onButtonClicked() {
        viewModel.profileImgUri.value = null
    }
}