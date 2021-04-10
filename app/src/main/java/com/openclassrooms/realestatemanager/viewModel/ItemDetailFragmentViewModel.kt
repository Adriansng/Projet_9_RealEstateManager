package com.openclassrooms.realestatemanager.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.model.Photo
import com.openclassrooms.realestatemanager.model.RealEstate
import com.openclassrooms.realestatemanager.model.RealEstateComplete
import com.openclassrooms.realestatemanager.model.Realtor
import com.openclassrooms.realestatemanager.repositories.PhotoRepository
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import com.openclassrooms.realestatemanager.repositories.RealtorRepository

class ItemDetailFragmentViewModel(private val realtorRepository: RealtorRepository,
                                  private val realEstateRepository: RealEstateRepository
): ViewModel() {

    // ------------------
    // REALTOR
    // ------------------

    // --- GET ---

    fun getRealtor(id: Long): LiveData<Realtor> = realtorRepository.getRealtor(id)

    fun getRealtorCurrent() : MutableLiveData<Realtor> = realtorRepository.getCurrentRealtor()

    // ------------------
    // REAL ESTATE
    // ------------------

    fun getRealEstate(id: Long): LiveData<RealEstateComplete> = realEstateRepository.getRealEstate(id)

    fun addRealEstate(realEstate: RealEstate) = realEstateRepository.addRealEstate(realEstate)

}