package com.ssafy.campinity.data.local.datasource.area

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "area")
data class Area(
    @PrimaryKey
    @ColumnInfo(name = "uid")
    val uid: Int,
    @ColumnInfo(name = "sido")
    val sido: String,
    @ColumnInfo(name = "gugun")
    val gugun: String,
    @ColumnInfo(name = "note_count")
    val noteCount: Int
)