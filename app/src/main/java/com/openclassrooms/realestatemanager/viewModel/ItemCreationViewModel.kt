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

class ItemCreationViewModel(private var realEstateRepository: RealEstateRepository,
                            private var realtorRepository: RealtorRepository,
                            private var photoRepository: PhotoRepository) : ViewModel() {

    // ------------------
    // PHOTO
    // ------------------

    // --- ADD ---

    fun insertPhoto(photo: Photo) = photoRepository.insertPhoto(photo)

    // --- DELETE ---

    fun deleteAllPhoto(id: Long) = photoRepository.deleteAll(id)

    // ------------------
    // REAL ESTATE
    // ------------------

    // --- GET ---

    fun getRealEstate(id: Long): LiveData<RealEstateComplete> = realEstateRepository.getRealEstate(id)

    fun getRealEstateLast(tableName: String): Long? = realEstateRepository.getRealEstateLast(tableName)

    // --- ADD ---

    fun addRealEstate(realEstate: RealEstate) = realEstateRepository.addRealEstate(realEstate)

    // --- INIT ---

    fun initSearchList() = realEstateRepository.initSearchList()

    // ------------------
    // REALTOR
    // ------------------

    // --- GET ---

    fun getRealtorCurrent(): MutableLiveData<Realtor> = realtorRepository.getCurrentRealtor()
}