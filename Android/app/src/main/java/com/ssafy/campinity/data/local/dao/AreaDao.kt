package com.ssafy.campinity.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ssafy.campinity.data.local.datasource.area.AreaGugunResponse
import com.ssafy.campinity.data.local.datasource.area.AreaSidoResponse
import com.ssafy.campinity.domain.entity.search.AreaEntity

@Dao
interface AreaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(areaEntity: AreaEntity)

    @Query("SELECT sido_name, SUM(campsite_count) campsite_count_all FROM area GROUP BY sido_name")
    fun getSidoAll(): List<AreaSidoResponse>

    @Query("SELECT gugun_name, campsite_count FROM area WHERE sido_name = :sidoName")
    fun getGugun(sidoName: String): List<AreaGugunResponse>
}