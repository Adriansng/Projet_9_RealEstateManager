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

/**
 * Created by Adrian SENEGAS 22/03/2021.
 */
class ItemCreationViewModel(private var realEstateRepository: RealEstateRepository,
                            private var realtorRepository: RealtorRepository,
                            private var photoRepository: PhotoRepository) : ViewModel() {

    // --- FOR DATA ---


    // ------------------
    // PHOTO
    // ------------------
    fun getListPhoto(idRealEstate: Long) : LiveData<List<Photo>> = photoRepository.getListPhoto(idRealEstate)
    fun insertPhoto(photo: Photo) = photoRepository.insertPhoto(photo)
    fun deleteAllPhoto(id: Long) = photoRepository.deleteAll(id)
    // ------------------
    // REAL ESTATE
    // ------------------

    fun getRealEstate(id : Long): RealEstate = realEstateRepository.getRealEstate(id)

    fun getRealEstateLast(tableName: String): Long? = realEstateRepository.getRealEstateLast(tableName)


     fun addRealEstate(realEstate: RealEstate) = realEstateRepository.addRealEstate(realEstate)

    // ------------------
    // REALTOR
    // ------------------

    fun getRealtorCurrent(): MutableLiveData<Realtor> = realtorRepository.getCurrentRealtor()
}