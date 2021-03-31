package com.openclassrooms.realestatemanager.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.model.RealEstate
import com.openclassrooms.realestatemanager.model.Realtor
import com.openclassrooms.realestatemanager.repositories.PhotoRepository
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import com.openclassrooms.realestatemanager.repositories.RealtorRepository

class MapViewModel(private val realtorRepository: RealtorRepository,
                   private val realEstateRepository: RealEstateRepository
): ViewModel() {


    // ------------------
    // REALTOR
    // ------------------

    // --- GET ---

    fun getRealtorCurrent() : MutableLiveData<Realtor> = realtorRepository.getCurrentRealtor()

    // ------------------
    // REAL ESTATE
    // ------------------

    fun getRealEstates(): LiveData<List<RealEstate>> = realEstateRepository.getRealEstates()

    fun getRealEstate(id: Long): RealEstate = realEstateRepository.getRealEstate(id)

}
