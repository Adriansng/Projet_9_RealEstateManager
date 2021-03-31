package com.openclassrooms.realestatemanager.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.widget.Toast
import androidx.room.TypeConverter
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.openclassrooms.realestatemanager.model.RealEstate
import java.util.*

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

    // Address to Location



}
