package com.openclassrooms.realestatemanager.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.sqlite.db.SimpleSQLiteQuery
import com.openclassrooms.realestatemanager.model.RealEstateComplete
import com.openclassrooms.realestatemanager.model.Realtor
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import com.openclassrooms.realestatemanager.repositories.RealtorRepository

class SearchViewModel(private val realtorRepository: RealtorRepository,
                      private val realEstateRepository: RealEstateRepository): ViewModel() {

    // ------------------
    // REALTOR
    // ------------------

    // --- GET ---

    fun getRealtorCurrent() : MutableLiveData<Realtor> = realtorRepository.getCurrentRealtor()

    // ------------------
    // REAL ESTATE
    // ------------------

    fun getEstatesBySearch(queryToConvert:String, args:ArrayList<Any>) : LiveData<List<RealEstateComplete>> {
        val query = SimpleSQLiteQuery(queryToConvert, args.toArray())
        return realEstateRepository.getEstatesBySearch(query)
    }

    fun setSearchList(it: List<RealEstateComplete>) {
        realEstateRepository.setSearchList(it)
    }

    fun initSearchList() = realEstateRepository.initSearchList()
}
