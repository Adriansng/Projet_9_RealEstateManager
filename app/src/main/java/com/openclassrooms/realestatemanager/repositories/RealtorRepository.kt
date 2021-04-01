package com.openclassrooms.realestatemanager.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.openclassrooms.realestatemanager.database.dao.RealtorDao
import com.openclassrooms.realestatemanager.model.Realtor


class RealtorRepository(
        private val realtorDao: RealtorDao
) {

    private var currentRealtor : MutableLiveData<Realtor> = MutableLiveData()

    // ------------------
    // GET
    // ------------------

    // --- REALTOR ---

    fun getRealtor(id: Long): Realtor = realtorDao.getRealtor(id)

    // --- CURRENT REALTOR ---

    fun getCurrentRealtor() : MutableLiveData<Realtor> = currentRealtor

    fun setCurrentRealtor(realtor: Realtor) = realtor.also { this.currentRealtor.value = it }

    // --- REALTORS ---

    fun getRealtors(): LiveData<List<Realtor>> = realtorDao.getRealtors()

    // ------------------
    // CREATE
    // ------------------

    fun createRealtor(realtor: Realtor) = realtorDao.insertRealtors(realtor)

}