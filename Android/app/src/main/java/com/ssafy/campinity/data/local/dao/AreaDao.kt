package com.ssafy.campinity.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.ssafy.campinity.domain.entity.search.AreaGugun

@Dao
interface AreaDao {

    @Query("SELECT gugun, note_count from ")
    fun getGuGun(): List<AreaGugun>
}