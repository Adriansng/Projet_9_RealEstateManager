package com.openclassrooms.realestatemanager.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.model.RealEstate
import com.openclassrooms.realestatemanager.model.Realtor
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import com.openclassrooms.realestatemanager.repositories.RealtorRepository

/**
 * Created by Adrian SENEGAS 22/03/2021.
 */
class ItemCreationViewModel(private var realEstateRepository: RealEstateRepository,
                            private var realtorRepository: RealtorRepository) : ViewModel() {

    // --- FOR DATA ---


    // ------------------
    // PHOTO
    // ------------------


    // ------------------
    // REAL ESTATE
    // ------------------

    fun getRealEstate(id : Long): RealEstate = realEstateRepository.getRealEstate(id)

     fun addRealEstate(realEstate: RealEstate){
        realEstateRepository.addRealEstate(realEstate)
    }

    // ------------------
    // REALTOR
    // ------------------

    fun getRealtorCurrent(): MutableLiveData<Realtor> = realtorRepository.getCurrentRealtor()
}