package com.ssafy.campinity.presentation.collection

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.view.WindowManager
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentCreateCollectionBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class CreateCollectionFragment :
    BaseFragment<FragmentCreateCollectionBinding>(R.layout.fragment_create_collection),
    CollectionDatePickerDialogListener {

    private val viewModel by viewModels<CollectionViewModel>()
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

    override fun initView() {
        binding.vm = viewModel
        initListener()
        setTextWatcher()
        observeState()
    }

    override fun onSubmitButtonClickled(date: String) {
        binding.tvDateInput.text = date
    }

    private fun initListener() {
        binding.apply {
            ivArrowLeft.setOnClickListener { popBackStack() }
            clAddPhoto.setOnClickListener { setAlbumView() }
            tvDateInput.setOnClickListener { getDate() }
            tvMakeReview.setOnClickListener { viewModel.createCollection() }
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

    private fun getDate() {
        val dialog = CollectionDatePickerDialog(requireContext(), this)
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setTextWatcher() {
        binding.apply {
            etDescription.addTextChangedListener {
                viewModel.content.value = binding.etDescription.text.toString()
            }
            etLocation.addTextChangedListener {
                viewModel.campsiteName.value = binding.etLocation.text.toString()
            }
        }
    }

    private fun setAlbumView() {
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
                    REQUEST_READ_STORAGE_PERMISSION
                )
            }
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

    companion object {
        const val REQUEST_READ_STORAGE_PERMISSION = 1
    }
}