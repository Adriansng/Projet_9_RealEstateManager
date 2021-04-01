package com.openclassrooms.realestatemanager.repositories

import androidx.lifecycle.LiveData
import androidx.sqlite.db.SimpleSQLiteQuery
import com.openclassrooms.realestatemanager.database.dao.RealEstateDao
import com.openclassrooms.realestatemanager.model.RealEstate
import com.openclassrooms.realestatemanager.model.RealEstateComplete


class RealEstateRepository(
        private val realEstateDao: RealEstateDao
) {

    // ------------------
    // GET
    // ------------------

    fun getRealEstates(): LiveData<List<RealEstateComplete>> = realEstateDao.getRealEstates()
    fun getRealEstate(id: Long): RealEstateComplete = realEstateDao.getRealEstate(id)

    // --- GET LAST CREATE ---
    fun getRealEstateLast(table: String): Long? = realEstateDao.getRealEstateLast(table)

    // --- GET SEARCH ---

    fun gesEstatesBySearch(query: SimpleSQLiteQuery) : LiveData<List<RealEstateComplete>>{
        return realEstateDao.getItemsBySearch(query)
    }

    // ------------------
    // CREATE
    // ------------------

     fun addRealEstate(realEstate: RealEstate) {
        realEstateDao.insertRealEstates(realEstate)
    }

}