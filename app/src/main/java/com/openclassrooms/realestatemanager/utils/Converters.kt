package com.openclassrooms.realestatemanager.utils

import androidx.room.TypeConverter
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters{

    @TypeConverter
    fun serializeLatLng(location: LatLng?): String? = location?.let {
        Gson().toJson(location)
    } ?: ""

    @TypeConverter
    fun deserializeLatLng(location: String): LatLng? {
        return fromJson<LatLng>(location)
    }

    private inline fun <reified T> fromJson(json: String): T {
        return Gson().fromJson(json, object: TypeToken<T>(){}.type)
    }

}
