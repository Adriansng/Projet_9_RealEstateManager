package com.openclassrooms.realestatemanager.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.model.RealEstate
import com.openclassrooms.realestatemanager.model.Realtor
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import com.openclassrooms.realestatemanager.repositories.RealtorRepository

/**
 * Created by Adrian SENEGAS 16/03/2021.
 */
class ItemListViewModel(private val realtorRepository: RealtorRepository,
                        private val realEstateRepository: RealEstateRepository): ViewModel() {

    // ------------------
    // REALTOR
    // ------------------

    // --- GET ---

    fun getRealtor(id: Long): Realtor = realtorRepository.getRealtor(id)

    fun getRealtors(): LiveData<List<Realtor>> = realtorRepository.getRealtors()

    // --- ADD ---

    fun addRealtor(name: String) = realtorRepository.createRealtor(Realtor(id = 0, name = name, prefEuro = false) )

    // --- UPDATE ---

    // ------------------
    // REAL ESTATE
    // ------------------

    fun getRealEstates(): LiveData<List<RealEstate>> = realEstateRepository.getRealEstates()

    // ------------------
    //  DEVICE
    // ------------------


    // --- UPDATE ---

    fun updateDeviceForRealtor(id: Long, device: Boolean) =
            realtorRepository.updateDeviceForRealtor(id, device)

}