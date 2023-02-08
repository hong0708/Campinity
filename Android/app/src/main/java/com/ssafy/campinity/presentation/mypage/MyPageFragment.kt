package com.ssafy.campinity.presentation.mypage

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.ssafy.campinity.ApplicationClass
import com.ssafy.campinity.R
import com.ssafy.campinity.common.util.BindingAdapters.setProfileImg
import com.ssafy.campinity.common.util.BindingAdapters.setProfileImgString
import com.ssafy.campinity.databinding.FragmentMyPageBinding
import com.ssafy.campinity.domain.entity.community.NoteQuestionTitle
import com.ssafy.campinity.presentation.base.BaseFragment
import com.ssafy.campinity.presentation.collection.CollectionDeleteFileDialog
import com.ssafy.campinity.presentation.collection.CreateCollectionFragment
import com.ssafy.campinity.presentation.collection.FileDeleteDialogListener
import com.ssafy.campinity.presentation.community.note.CommunityNoteListAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class MyPageFragment :
    BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page),
    LogoutDialogListener, FileDeleteDialogListener {

    private val myPageViewModel by activityViewModels<MyPageViewModel>()
    private val communityNoteListAdapter by lazy {
        CommunityNoteListAdapter(this::showDialog)
    }
    private val fromAlbumActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        result.data?.let {
            if (it.data != null) {
                myPageViewModel.setProfileImg(
                    it.data as Uri,
                    File(absolutelyPath(it.data, requireContext()))
                )
                Glide.with(requireContext())
                    .load(it.data as Uri)
                    .placeholder(R.drawable.ic_profile_image)
                    .error(R.drawable.ic_profile_image)
                    .circleCrop()
                    .into(binding.ivProfileImage)
            }
        }
    }

    override fun initView() {
        // myPage
        setData()
        initRecyclerView()
        initListener()
        initSpinner()
        // edit
        setTextWatcher()
        myPageViewModel.checkSame()
        observeState()
    }

    private fun observeState() {
        myPageViewModel.isSuccess.observe(viewLifecycleOwner) {
            if (it == true) showToast("프로필이 수정되었습니다.")
        }
        myPageViewModel.isDuplicate.observe(viewLifecycleOwner) {
            if (it == false) {
                binding.btnConfirm.apply {
                    setBackgroundResource(R.drawable.bg_rect_grey_radius10)
                    isEnabled = false
                }
            } else {
                binding.btnConfirm.apply {
                    setBackgroundResource(R.drawable.bg_rect_bilbao_radius10)
                    isEnabled = true
                }
            }
        }
        myPageViewModel.detailData.observe(viewLifecycleOwner) {
            val dialog = ReviewNoteDialog(requireContext(), it!!)
            dialog.show()
        }
    }

    private fun setData() {
        // myPage
        myPageViewModel.nickname.observe(viewLifecycleOwner) {
            binding.tvNickname.text = it
        }
        myPageViewModel.profileImgUri.observe(viewLifecycleOwner) {
            binding.ivProfile.setProfileImg(it)
        }
        myPageViewModel.profileImgStr.observe(viewLifecycleOwner) {
            binding.ivProfile.setProfileImgString(it)
        }
        myPageViewModel.getInfo()

        // edit
        myPageViewModel.profileImgUri.observe(viewLifecycleOwner) {
            binding.ivProfileImage.setProfileImg(it)
        }
        myPageViewModel.profileImgStr.observe(viewLifecycleOwner) {
            binding.ivProfileImage.setProfileImgString(it)
        }
        binding.etNickname.setText(myPageViewModel.nickname.value)
        binding.ivProfileImage.setProfileImgString(myPageViewModel.profileImgStr.value)
    }

    private fun initListener() {

        val slidePanel = binding.slMyPage
        slidePanel.addPanelSlideListener(PanelEventListener())

        binding.clEditProfile.setOnClickListener {
            // 닫힌 상태일 경우 열기
            if (slidePanel.panelState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            }
        }

        binding.ivArrowLeft.setOnClickListener {
            popBackStack()
        }

        binding.tvLogOut.setOnClickListener {
            val dialog = LogoutDialog(requireContext(), this)
            dialog.show()
        }

        // edit
        binding.apply {
            ivProfileImage.setOnClickListener { setAlbumView() }
            btnBack.setOnClickListener { popBackStack() }
            btnCheckDuplication.setOnClickListener {
                if (myPageViewModel.nickname.value == null) {
                    showToast("닉네임을 입력해주세요.")
                } else {
                    myPageViewModel.checkDuplication()
                    if (myPageViewModel.isDuplicate.value == true) {
                        showToast("중복된 닉네임입니다.")
                    } else {
                        showToast("사용할 수 있는 닉네임입니다.")
                    }
                }
            }
            // 수정 확인 버튼을 눌렀을 때
            btnConfirm.setOnClickListener {
                myPageViewModel.isSuccess.value = false
                ApplicationClass.preferences.nickname = myPageViewModel.nickname.value
                if (myPageViewModel.profileImgUri.value != null) {
                    myPageViewModel.updateProfile()
                } else {
                    if (myPageViewModel.profileImgStr.value == null) {
                        myPageViewModel.updateProfileWithoutImg()
                    } else {
                        myPageViewModel.updateProfileWithExistingImg()
                    }
                }
                if (slidePanel.panelState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    slidePanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                }
            }
        }
    }

    private fun initRecyclerView() {
        myPageViewModel.getNotes()
        binding.rvMyNote.apply {
            adapter = communityNoteListAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    private fun initSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.category_array,
            R.layout.spinner_txt
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerCategory.adapter = adapter
        }

        binding.spinnerCategory.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if (p0?.getItemAtPosition(p2).toString() == "자유") {
                        myPageViewModel.etcNotesListData.observe(viewLifecycleOwner) { response ->
                            response?.let {
                                if (response.isEmpty()) {
                                    binding.rvMyNote.visibility = View.GONE
                                    binding.clEmptyCollection.visibility = View.VISIBLE
                                } else {
                                    binding.clEmptyCollection.visibility = View.GONE
                                    communityNoteListAdapter.setNote(it.map { info ->
                                        NoteQuestionTitle(
                                            info.content,
                                            info.createdAt,
                                            info.messageId
                                        )
                                    })
                                }
                            }
                        }
                    } else {
                        myPageViewModel.reviewNotesListData.observe(viewLifecycleOwner) { response ->
                            response?.let {
                                if (response.isEmpty()) {
                                    binding.rvMyNote.visibility = View.GONE
                                    binding.clEmptyCollection.visibility = View.VISIBLE
                                } else {
                                    binding.clEmptyCollection.visibility = View.GONE
                                    communityNoteListAdapter.setNote(it.map { info ->
                                        NoteQuestionTitle(
                                            info.content,
                                            info.createdAt,
                                            info.messageId
                                        )
                                    })
                                }
                            }
                        }
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    return
                }
            }
    }

    private fun showDialog(noteQuestionId: String) {
        myPageViewModel.getDetailData(noteQuestionId)
    }

    // 로그아웃
    override fun onSubmitButtonClicked() {
        myPageViewModel.requestLogout()
        myPageViewModel.isLoggedOut.observe(viewLifecycleOwner) {
            if (it == true) {
                ApplicationClass.preferences.apply {
                    accessToken = null
                    refreshToken = null
                    fcmToken = null
                    isLoggedIn = false
                }
                navigate(MyPageFragmentDirections.actionMyPageFragmentToOnBoardingFragment())
                showToast("로그아웃 되었습니다.")
            } else {
                showToast("다시 시도해주세요.")
            }
        }
    }

    private fun setTextWatcher() {
        binding.etNickname.addTextChangedListener {
            myPageViewModel.nickname.value = binding.etNickname.text.toString()
        }
    }

    private fun setAlbumView() {
        if (myPageViewModel.profileImgUri.value == null && myPageViewModel.profileImgStr.value == null) {
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
                        CreateCollectionFragment.REQUEST_READ_STORAGE_PERMISSION
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

    // 파일 삭제 버튼 클릭시
    override fun onConfirmButtonClicked() {
        myPageViewModel.profileImgStr.value = null
        myPageViewModel.profileImgUri.value = null
        myPageViewModel.profileImgMultiPart = null
    }

    // 이벤트 리스너
    inner class PanelEventListener : SlidingUpPanelLayout.PanelSlideListener {
        // 패널이 슬라이드 중일 때
        override fun onPanelSlide(panel: View?, slideOffset: Float) {}
        // 패널의 상태가 변했을 때
        override fun onPanelStateChanged(
            panel: View?,
            previousState: SlidingUpPanelLayout.PanelState?,
            newState: SlidingUpPanelLayout.PanelState?
        ) {}
    }
}