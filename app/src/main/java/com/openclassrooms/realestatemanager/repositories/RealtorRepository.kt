package com.openclassrooms.realestatemanager.repositories

import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.database.dao.RealtorDao
import com.openclassrooms.realestatemanager.model.Realtor


class RealtorRepository(
        private val realtorDao: RealtorDao
) {

    // ------------------
    // GET
    // ------------------

    fun getRealtor(id: Long): Realtor = realtorDao.getRealtor(id)

    fun getRealtors(): LiveData<List<Realtor>> = realtorDao.getRealtors()

    // ------------------
    // CREATE
    // ------------------

    fun createRealtor(realtor: Realtor) = realtorDao.insertRealtors(realtor)


    // ------------------
    // UPDATE
    // ------------------

    // --- PREF DEVICE ---

    fun updateDeviceForRealtor(id: Long, device: Boolean){
        val realtor = getRealtor(id)
        realtor.prefEuro = device
        realtorDao.updateRealtor(realtor)
    }

    // --- CHANGE NAME ---

    fun updateNameForRealtor(id: Long, name: String){
        val realtor = getRealtor(id)
        realtor.name = name
        realtorDao.updateRealtor(realtor)
    }


}