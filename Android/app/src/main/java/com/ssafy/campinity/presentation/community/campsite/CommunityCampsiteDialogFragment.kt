package com.ssafy.campinity.presentation.community.campsite

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.view.WindowManager
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.ssafy.campinity.R
import com.ssafy.campinity.common.util.Permission
import com.ssafy.campinity.databinding.FragmentCommunityCampsiteDialogBinding
import com.ssafy.campinity.domain.entity.community.MarkerLocation
import com.ssafy.campinity.presentation.base.BaseFragment
import com.ssafy.campinity.presentation.collection.CollectionDeleteDialogListener
import com.ssafy.campinity.presentation.collection.CollectionDeleteFileDialog
import java.io.File

class CommunityCampsiteDialogFragment :
    BaseFragment<FragmentCommunityCampsiteDialogBinding>(R.layout.fragment_community_campsite_dialog),
    CollectionDeleteDialogListener,
    CommunityCampsiteMarkerDialogListener {

    private val args by navArgs<CommunityCampsiteDialogFragmentArgs>()
    private val viewModel by activityViewModels<CommunityCampsiteViewModel>()
    private val fromAlbumActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        result.data?.let {
            if (it.data != null) {
                viewModel.setPicture(
                    it.data as Uri,
                    File(absolutelyPath(it.data, requireContext()))
                )
            }
        }
    }

    override fun onConfirmButtonClicked() {
        viewModel.file.value = null
    }

    override fun onSubmitButtonClicked(longitude: Double, latitude: Double) {
        viewModel.markerLocation.value = MarkerLocation(latitude, longitude)
    }

    override fun initView() {
        setTextWatcher()
        observeState()

        binding.communityCampsiteViewModel = viewModel

        binding.apply {
            tvReviewTitle.text = args.dialogType

            ivCloseWriteReviewNoteDialog.setOnClickListener {
                popBackStack()
            }

            clAddPhoto.setOnClickListener { setAlbumView() }

            if (args.dialogType == "리뷰 쪽지") {
                tvSelectMarkerLocation.visibility = View.VISIBLE

                tvSelectMarkerLocation.setOnClickListener {
                    CommunityCampsiteMarkerDialog(
                        requireActivity(),
                        this@CommunityCampsiteDialogFragment
                    ).show()
                }
                tvMakeReview.setOnClickListener {
                    //communityCampsiteFreeReviewDialogInterface.createFreeReviewNote()
                    viewModel.createCommunityCampsiteMessage(
                        "리뷰",
                        args.campsiteId
                    )
                    //popBackStack()
                }

            } else {
                tvSelectMarkerLocation.visibility = View.GONE
                tvMakeReview.setOnClickListener {
                    //communityCampsiteFreeReviewDialogInterface.createFreeReviewNote()
                    viewModel.createCommunityCampsiteMessage(
                        "자유",
                        args.campsiteId
                    )
                    //popBackStack()
                }
            }
        }
    }

    private fun setTextWatcher() {
        binding.apply {
            etUserReview.addTextChangedListener {
                viewModel.content.value = binding.etUserReview.text.toString()
            }
        }
    }

    private fun observeState() {
        viewModel.isSucceed.observe(viewLifecycleOwner) {
            when (it) {
                true -> popBackStack()
                else -> {}
            }
        }
    }

    private fun setAlbumView() {
        if (viewModel.file.value == null) {
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
                        Permission.REQUEST_READ_STORAGE_PERMISSION
                    )
                }
            }
        } else {
            val dialog = CollectionDeleteFileDialog(requireContext(), this)
            dialog.setCanceledOnTouchOutside(true)
            dialog.show()
            dialog.window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
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
}