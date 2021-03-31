package com.openclassrooms.realestatemanager.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.model.Photo
import com.openclassrooms.realestatemanager.model.RealEstate
import com.openclassrooms.realestatemanager.model.Realtor
import com.openclassrooms.realestatemanager.repositories.PhotoRepository
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import com.openclassrooms.realestatemanager.repositories.RealtorRepository

class ItemDetailFragmentViewModel(private val realtorRepository: RealtorRepository,
                                  private val realEstateRepository: RealEstateRepository,
                                  private val photoRepository: PhotoRepository
): ViewModel() {

    // ------------------
    // REALTOR
    // ------------------

    // --- GET ---

    fun getRealtor(id: Long): Realtor = realtorRepository.getRealtor(id)

    fun getRealtorCurrent() : MutableLiveData<Realtor> = realtorRepository.getCurrentRealtor()

    fun getRealtors(): LiveData<List<Realtor>> = realtorRepository.getRealtors()

    fun addRealEstate(realEstate: RealEstate) = realEstateRepository.addRealEstate(realEstate)

    // --- ADD ---

    fun addRealtor(name: String) = realtorRepository.createRealtor(Realtor(id = 0, name = name, prefEuro = false) )

    // --- UPDATE ---

    // ------------------
    // REAL ESTATE
    // ------------------

    fun getRealEstate(id: Long): RealEstate = realEstateRepository.getRealEstate(id)

    // ------------------
    // REALTOR
    // ------------------

    fun getPhotos(id: Long): LiveData<List<Photo>> = photoRepository.getListPhoto(id)

}