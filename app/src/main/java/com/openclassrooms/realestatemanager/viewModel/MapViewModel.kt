package com.openclassrooms.realestatemanager.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.model.RealEstateComplete
import com.openclassrooms.realestatemanager.model.Realtor
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import com.openclassrooms.realestatemanager.repositories.RealtorRepository

class MapViewModel(private val realtorRepository: RealtorRepository,
                   private val realEstateRepository: RealEstateRepository
): ViewModel() {

    // ------------------
    // REALTOR
    // ------------------

    fun getRealtorCurrent() : MutableLiveData<Realtor> = realtorRepository.getCurrentRealtor()

    // ------------------
    // REAL ESTATE
    // ------------------

    fun getRealEstates(): LiveData<List<RealEstateComplete>> = realEstateRepository.getRealEstates()

}
