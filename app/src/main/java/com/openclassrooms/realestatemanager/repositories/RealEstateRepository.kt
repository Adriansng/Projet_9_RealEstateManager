package com.openclassrooms.realestatemanager.repositories

import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.database.dao.RealEstateDao
import com.openclassrooms.realestatemanager.model.RealEstate


class RealEstateRepository(
        private val realEstateDao: RealEstateDao
) {

    // ------------------
    // GET
    // ------------------
    fun getRealEstates(): LiveData<List<RealEstate>> = realEstateDao.getRealEstates()
    // ------------------
    // CREATE
    // ------------------

    // ------------------
    // UPDATE
    // ------------------

    // ------------------
    // CHANGE DEVICE
    // ------------------



}