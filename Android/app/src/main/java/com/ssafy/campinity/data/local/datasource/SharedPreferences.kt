package com.ssafy.campinity.data.local.datasource

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ssafy.campinity.domain.entity.search.AreaSido
import org.json.JSONArray
import org.json.JSONObject

class SharedPreferences(private val context: Context) {

    private val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    var accessToken: String?
        get() = prefs.getString("accessToken", null)
        set(value) = prefs.edit().putString("accessToken", value).apply()

    var refreshToken: String?
        get() = prefs.getString("refreshToken", null)
        set(value) = prefs.edit().putString("refreshToken", value).apply()

    var areaList: List<AreaSido>
        get() {
            val listType: TypeToken<List<AreaSido>> = object : TypeToken<List<AreaSido>>() {}
            val area = prefs.getString("area", null)

            return Gson().fromJson(area, listType.type)
        }
        set(_) {
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
                        1 -> sidoObject.put("campsiteCountAll", s.toInt())
                        else -> {
                            val gugun = s.split("(")[0]
                            val campsiteCount = s.split("(")[1].split(")")[0].toInt()
                            sidoObject.put("gugunList", gugunArray.put(JSONObject().apply {
                                this.put("gugun", gugun)
                                this.put("campsiteCount", campsiteCount)
                            }))
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