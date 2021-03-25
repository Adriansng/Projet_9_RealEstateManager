package com.openclassrooms.realestatemanager.repositories

import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.database.dao.RealEstateDao
import com.openclassrooms.realestatemanager.model.RealEstate
import com.openclassrooms.realestatemanager.service.RetrofitService


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
     fun addRealEstate(realEstate: RealEstate) {
        realEstateDao.insertRealEstates(realEstate)
        val realEstateLocation = realEstate.copy(location = getGeocoding(realEstate.address+realEstate.city))
        realEstateDao.insertRealEstates(realEstateLocation)
    }


    // ------------------
    // UPDATE
    // ------------------

    // ------------------
    // GEOCODING
    // ------------------

    private val googleService = RetrofitService().getInterface()

     private fun getGeocoding(address: String) : LatLng? {
        val results = googleService?.getGeocoding(address, "BuildConfig.MAPS_API_KEY")?.results
        val location = results?.getOrNull(0)?.geometry?.location
        return location?.let {
            it.lat?.let { it1 -> it.lng?.let { it2 -> LatLng(it1, it2) } }
        }
    }


}