package com.ssafy.campinity.presentation.community.campsite

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.WindowManager
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.navigation.navArgs
import com.ssafy.campinity.common.util.BindingAdapters.setCollectionImgUri
import com.ssafy.campinity.common.util.Permission
import com.ssafy.campinity.common.util.showToastMessage
import com.ssafy.campinity.databinding.ActivityCommunityCampsiteMessageDialogBinding
import com.ssafy.campinity.domain.entity.community.MarkerLocation
import com.ssafy.campinity.presentation.collection.CollectionDeleteFileDialog
import com.ssafy.campinity.presentation.collection.FileDeleteDialogListener
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class CommunityCampsiteDialogActivity :
    AppCompatActivity(),
    FileDeleteDialogListener,
    CommunityCampsiteMarkerDialogListener {

    private val args by navArgs<CommunityCampsiteDialogActivityArgs>()
    private lateinit var binding: ActivityCommunityCampsiteMessageDialogBinding
    private lateinit var type: String
    private lateinit var campsiteId: String
    private val viewModel by viewModels<CommunityCampsiteDialogViewModel>()

    private val fromAlbumActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        result.data?.let {
            if (it.data != null) {
                viewModel.setPicture(
                    it.data as Uri,
                    File(absolutelyPath(it.data, this))
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunityCampsiteMessageDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        type = args.dialogType
        campsiteId = args.campsiteId

        setTextWatcher()
        initView()
        observeState()
    }

    override fun onConfirmButtonClicked() {
        viewModel.file.value = null
    }

    override fun onSubmitButtonClicked(longitude: Double, latitude: Double) {
        viewModel.markerLocation.value = MarkerLocation(latitude, longitude)
    }

    private fun setTextWatcher() {
        binding.apply {
            etUserReview.addTextChangedListener {
                viewModel.content.value = binding.etUserReview.text.toString()
            }
        }
    }

    private fun observeState() {
        viewModel.isSucceed.observe(this) {
            when (it) {
                true -> onBackPressed()
                else -> {}
            }
        }

        viewModel.file.observe(this) { response ->
            response.let {
                binding.ivCommunityUserPhoto.setCollectionImgUri(response)
            }
        }
    }

    private fun initView() {

        binding.apply {
            binding.tvReviewTitle.text = type
            binding.communityCampsiteDialogViewModel = viewModel

            ivCloseWriteReviewNoteDialog.setOnClickListener {
                onBackPressed()
            }

            ivCommunityUserPhoto.setOnClickListener { setAlbumView() }

            if (type == "리뷰 쪽지") {
                tvSelectMarkerLocation.visibility = View.VISIBLE

                tvSelectMarkerLocation.setOnClickListener {
                    CommunityCampsiteMarkerDialog(
                        this@CommunityCampsiteDialogActivity,
                        this@CommunityCampsiteDialogActivity
                    ).show()
                }

                tvMakeReview.setOnClickListener {
                    if (viewModel.content.value == "" &&
                        viewModel.markerLocation.value == MarkerLocation(0.0, 0.0)
                    )
                        showToastMessage("쪽지 내용과 마커 위치를 입력해주세요.")
                    else if (viewModel.content.value == "" && viewModel.markerLocation.value != null)
                        showToastMessage("쪽지 내용을 입력해주세요.")
                    else if (viewModel.content.value != "" &&
                        viewModel.markerLocation.value == MarkerLocation(0.0, 0.0)
                    )
                        showToastMessage("마커 위치를 입력해주세요.")
                    else
                        viewModel.createCommunityCampsiteMessage(
                            "리뷰",
                            campsiteId
                        )
                }
            } else {
                tvSelectMarkerLocation.visibility = View.GONE
                tvMakeReview.setOnClickListener {
                    if (viewModel.content.value == "")
                        showToastMessage("쪽지 내용을 입력해주세요.")
                    else
                        viewModel.createCommunityCampsiteMessage(
                            "자유",
                            campsiteId
                        )
                }
            }
        }
    }

    private fun setAlbumView() {
        if (viewModel.file.value == null) {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    this,
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
                        this,
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        Permission.REQUEST_READ_STORAGE_PERMISSION
                    )
                }
            }
        } else {
            val dialog = CollectionDeleteFileDialog(this, this)
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