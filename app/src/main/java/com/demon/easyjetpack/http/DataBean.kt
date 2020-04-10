package com.demon.easyjetpack.http

import com.demon.easyjetpack.data.Constants
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import org.json.JSONObject
import java.lang.reflect.Type

/**
 * @author DeMon
 * Created on 2020/1/13.
 * E-mail 757454343@qq.com
 * Desc:
 */
class DataBean {

    @SerializedName("HeWeather6")
    var heWeather6: List<Any> = listOf()

    private val gson: Gson = Gson()

    fun getData(): String {
        return gson.toJson(heWeather6[0])

    }

    fun getKeyData(key: String): String {
        var jsonObject = JSONObject(gson.toJson(heWeather6[0]))
        return jsonObject.get(key).toString()
    }


    fun <T> getDataBean(key: String, cls: Class<T>): T {
        return gson.fromJson<T>(getKeyData(key), cls)
    }

    fun <T> getDataList(key: String, type: Type): T {

        return gson.fromJson(getKeyData(key), type)
    }


    fun getStatus(): String {
        var jsonObject = JSONObject(gson.toJson(heWeather6[0]))
        if (jsonObject.has(Constants.STATUS)) {
            return jsonObject.getString(Constants.STATUS)
        }
        return ""
    }


}