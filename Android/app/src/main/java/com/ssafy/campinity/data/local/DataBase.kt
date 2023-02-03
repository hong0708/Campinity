package com.ssafy.campinity.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ssafy.campinity.data.local.dao.AreaDao
import com.ssafy.campinity.domain.entity.search.AreaEntity

@Database(entities = [AreaEntity::class], version = 1)

abstract class AreaDataBase : RoomDatabase() {
    abstract fun areaDao(): AreaDao
}