package com.ecomm.app.db

import androidx.room.TypeConverter
import com.ecomm.app.models.Rating
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {
    var gson = Gson()

   /* @TypeConverter
    fun stringToPriceList(data: String?): List<Price?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object : TypeToken<List<Price?>?>() {}.type
        return gson.fromJson<List<Price?>>(data, listType)
    }

    @TypeConverter
    fun priceListToString(someObjects: List<Price?>?): String? {
        return gson.toJson(someObjects)
    }*/

    @TypeConverter
    fun objToString(rating: Rating): String {
        return gson.toJson(rating)
    }

    @TypeConverter
    fun stringToObj(ratingString: String): Rating {
        val objType : Type = object : TypeToken<Rating>() {}.type
        return gson.fromJson(ratingString, objType)
    }
}