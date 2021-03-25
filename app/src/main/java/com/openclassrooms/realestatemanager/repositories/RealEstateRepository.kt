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
    fun getRealEstate(id: Long): RealEstate = realEstateDao.getRealEstate(id)
    fun getRealEstateLast(table: String): Long? = realEstateDao.getRealEstateLast(table)


    // ------------------
    // CREATE
    // ------------------
    fun addRealEstate(realEstate: RealEstate) = realEstateDao.insertRealEstates(realEstate)


    // ------------------
    // UPDATE
    // ------------------

    // ------------------
    // CHANGE DEVICE
    // ------------------



}