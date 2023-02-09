package com.ssafy.campinity.presentation.community.note

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.ActivityCommunityNoteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityNoteActivity: AppCompatActivity() {

    private lateinit var binding: ActivityCommunityNoteBinding
    private lateinit var navController: NavController
    val viewModel by viewModels<CommunityNoteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_community_note) as NavHostFragment
        navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.navigation_community_note)
        navController.graph = navGraph
    }
}