package com.openclassrooms.realestatemanager.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.model.RealEstate
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository

/**
 * Created by Adrian SENEGAS 22/03/2021.
 */
class ItemCreationViewModel(private var realEstateRepository: RealEstateRepository) : ViewModel() {

    // --- FOR DATA ---


    // ------------------
    // PHOTO
    // ------------------


    // ------------------
    // REAL ESTATE
    // ------------------

     fun addRealEstate(realEstate: RealEstate){
        realEstateRepository.addRealEstate(realEstate)
    }

}