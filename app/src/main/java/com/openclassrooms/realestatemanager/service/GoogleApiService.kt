package com.openclassrooms.realestatemanager.service

import com.openclassrooms.realestatemanager.model.POJO.Geocoding
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleApiService {

    // CALL API GEOCODING
    @GET("geocode/json")
    fun getGeocoding(
            @Query("address", encoded = true) address: String?,
            @Query("key", encoded = true) key: String?
    ) : Geocoding
}