package com.openclassrooms.realestatemanager.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.sqlite.db.SimpleSQLiteQuery
import com.openclassrooms.realestatemanager.model.RealEstate
import com.openclassrooms.realestatemanager.model.RealEstateComplete
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

    fun getRealtorCurrent() : MutableLiveData<Realtor> = realtorRepository.getCurrentRealtor()

    fun setRealtorCurrent(realtor : Realtor) = realtorRepository.setCurrentRealtor(realtor)

    fun getRealtors(): LiveData<List<Realtor>> = realtorRepository.getRealtors()

    // --- ADD ---

    fun addRealtor(realtor: Realtor) = realtorRepository.createRealtor(realtor )

    // --- UPDATE ---

    // ------------------
    // REAL ESTATE
    // ------------------

    fun getRealEstates(): LiveData<List<RealEstateComplete>> = realEstateRepository.getRealEstates()

    fun getEstatesBySearch(queryToConvert:String, args:ArrayList<Any>) : LiveData<List<RealEstateComplete>> {
        val query = SimpleSQLiteQuery(queryToConvert,args.toArray())
        return realEstateRepository.gesEstatesBySearch(query)
    }

    // ------------------
    //  DEVICE
    // ------------------


    // --- UPDATE ---

    fun updateDeviceForRealtor(id: Long, device: Boolean) =
            realtorRepository.updateDeviceForRealtor(id, device)

}