package com.openclassrooms.realestatemanager.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Adrian SENEGAS 25/02/2021.
 */
class RetrofitService {
    // --- CREATE INSTANCE RETROFIT ---
    private val baseUrlApi = "https://maps.googleapis.com/maps/api/"

    private val retrofit = Retrofit.Builder()
            .baseUrl(baseUrlApi)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun getInterface(): GoogleApiService? {
        return retrofit.create(GoogleApiService::class.java)
    }

}