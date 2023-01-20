package com.ssafy.campinity.presentation.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.campinity.common.util.getHashKey
import com.ssafy.campinity.databinding.ActivitySearchBinding
import net.daum.mf.map.api.MapView


class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rlMapView.addView(MapView(this@SearchActivity))

        getHashKey(this@SearchActivity)
    }
}