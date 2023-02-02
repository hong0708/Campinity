package com.ssafy.campinity.data.local.datasource

import android.content.Context
import android.util.Log
import com.ssafy.campinity.domain.entity.search.AreaSido
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class SharedPreferences(private val context: Context) {

    private val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    var accessToken: String?
        get() = prefs.getString("accessToken", null)
        set(value) = prefs.edit().putString("accessToken", value).apply()

    var refreshToken: String?
        get() = prefs.getString("refreshToken", null)
        set(value) = prefs.edit().putString("refreshToken", value).apply()

    var areaList: String?
        get() {
            val area = prefs.getString("area", null)
            val areaList: MutableList<AreaSido>
            if (area != null) {
                try {
                    val sidoArray = JSONArray(area)
                    val temp = arrayListOf<String>()
                    for (i in area.indices) {
                        temp.add(sidoArray.optString(i))
                    }
                    Log.d("areaList", "areaList[0]: ${temp[0]}")
                } catch (e: JSONException) {
                    Log.e("getAreaList", "Cannot get AreaList from SharedPreferences: $e")
                }
            } else {
                Log.d("areaList", "area is null")
            }

            return ""
        }
        set(value) {
            val editor = prefs.edit()
            val area = listOf(
                "gangwon",
                "gyeonggi",
                "gyeongsang_south",
                "gyeongsang_north",
                "gwangju",
                "daegu",
                "daejeon",
                "busan",
                "seoul",
                "sejong",
                "ulsan",
                "incheon",
                "jeonla_south",
                "jeonla_north",
                "jeju",
                "chungcheong_south",
                "chungcheong_north"
            )

            area.forEach { sido ->
                val resId = context.resources.getIdentifier(
                    "area_$sido", "array", context.packageName
                )
                val stringArray = context.resources.getStringArray(resId)
                val sidoArray = JSONArray()
                val sidoObject = JSONObject()
                val gugunArray = JSONArray()

                stringArray.forEachIndexed { index, s ->
                    when (index) {
                        0 -> sidoObject.put("sido", s)
                        1 -> sidoObject.put("campsiteCountAll", s)
                        else -> {
                            val gugun = s.split("(")[0]
                            val campsiteCount = s.split("(")[1].split(")")[0]
                            sidoObject.put(
                                "gugunList",
                                gugunArray.put(
                                    JSONObject().apply {
                                        this.put("gugun", gugun)
                                        this.put("campsiteCount", campsiteCount)
                                    }
                                )
                            )
                        }
                    }
                    sidoArray.put(sidoObject)
                }
                editor.putString("area", sidoArray.toString())
            }
            editor.apply()
        }

    fun clearPreferences() {
        prefs.edit().clear().apply()
    }
}