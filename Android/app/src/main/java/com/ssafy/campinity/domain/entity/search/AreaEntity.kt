package com.ssafy.campinity.domain.entity.search

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Fts4
@Entity(tableName = "area")
@Parcelize
class AreaEntity(
    @ColumnInfo(name = "sido_name") val sidoName: String,
    @ColumnInfo(name = "gugun_name") val gugunName: String,
    @ColumnInfo(name = "campsite_count") val campsiteCount: Int
) : Parcelable {
    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}