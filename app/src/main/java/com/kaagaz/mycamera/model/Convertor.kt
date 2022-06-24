package com.kaagaz.mycamera.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import java.util.*

class Convertor {

    @TypeConverter
    fun listToJson(value: List<ImageModel>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<ImageModel>::class.java).toList()
}