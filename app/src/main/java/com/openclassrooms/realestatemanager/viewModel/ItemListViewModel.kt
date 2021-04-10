package com.openclassrooms.realestatemanager.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.sqlite.db.SimpleSQLiteQuery
import com.openclassrooms.realestatemanager.model.RealEstateComplete
import com.openclassrooms.realestatemanager.model.Realtor
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import com.openclassrooms.realestatemanager.repositories.RealtorRepository

class ItemListViewModel(private val realtorRepository: RealtorRepository,
                        private val realEstateRepository: RealEstateRepository): ViewModel() {

    // ------------------
    // REALTOR
    // ------------------

    // --- GET ---

    fun getRealtor(id: Long): LiveData<Realtor> = realtorRepository.getRealtor(id)

    fun getRealtorCurrent() : MutableLiveData<Realtor> = realtorRepository.getCurrentRealtor()

    fun setRealtorCurrent(realtor : Realtor) = realtorRepository.setCurrentRealtor(realtor)

    fun getRealtors(): LiveData<List<Realtor>> = realtorRepository.getRealtors()

    // --- ADD ---

    fun addRealtor(realtor: Realtor) = realtorRepository.createRealtor(realtor )

    // ------------------
    // REAL ESTATE
    // ------------------

    fun getRealEstates(): LiveData<List<RealEstateComplete>> = realEstateRepository.getRealEstates()

}