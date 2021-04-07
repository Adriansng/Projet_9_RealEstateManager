package com.openclassrooms.realestatemanager.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.sqlite.db.SimpleSQLiteQuery
import com.openclassrooms.realestatemanager.database.dao.RealEstateDao
import com.openclassrooms.realestatemanager.model.RealEstate
import com.openclassrooms.realestatemanager.model.RealEstateComplete


class RealEstateRepository(
        private val realEstateDao: RealEstateDao
) {

    private var searchList : MutableLiveData<List<RealEstateComplete>> = MutableLiveData()
    // ------------------
    // GET
    // ------------------

    fun getRealEstates(): LiveData<List<RealEstateComplete>> {
        return if(searchList.value!!.isNotEmpty()){
            searchList
        }else{
            realEstateDao.getRealEstates()
        }
    }

    fun getRealEstate(id: Long): RealEstateComplete = realEstateDao.getRealEstate(id)

    // --- GET LAST CREATE ---
    fun getRealEstateLast(table: String): Long? = realEstateDao.getRealEstateLast(table)

    // --- GET SEARCH ---

    fun getEstatesBySearch(query: SimpleSQLiteQuery) : LiveData<List<RealEstateComplete>>{
        return realEstateDao.getItemsBySearch(query)
    }

    // ------------------
    // CREATE
    // ------------------

     fun addRealEstate(realEstate: RealEstate) {
        realEstateDao.insertRealEstates(realEstate)
    }

    // ------------------
    // SEARCH LIST
    // ------------------

    fun setSearchList(it: List<RealEstateComplete>) {
        searchList.value = it
    }

    fun initSearchList(){
        searchList.value = null
    }

}