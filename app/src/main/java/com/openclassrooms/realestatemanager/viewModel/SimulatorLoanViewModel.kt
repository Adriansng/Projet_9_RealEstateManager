package com.openclassrooms.realestatemanager.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.model.Realtor
import com.openclassrooms.realestatemanager.repositories.RealtorRepository

class SimulatorLoanViewModel(private val realtorRepository: RealtorRepository): ViewModel() {

    // --- FOR DATA ---

    val interest: MutableLiveData<Double> = MutableLiveData()
    val mount: MutableLiveData<Double> = MutableLiveData()
    val total: MutableLiveData<Double> = MutableLiveData()

    // ------------------
    // SIMULATOR LOAN
    // ------------------

    // --- UPDATE VALUES ---

    private fun updateInterest(interestValue: Double) {
        interest.value = interestValue
    }

    private fun updateMount(mountValue: Double){
        mount.value = mountValue
    }

    private fun updateTotal(totalValue: Double){
        total.value = totalValue
    }

    // --- CALCULATOR ---

    fun calculatorLoan(amount: Int, contribution: Int, interest: Double, term: Int){
        val interestLoan : Double = (amount - contribution) * (interest/100)
        val totalLoan : Double = amount + interestLoan
        val termLoan : Double = totalLoan/ (term*12)
        updateInterest(interestLoan)
        updateMount(termLoan)
        updateTotal(totalLoan)
    }

    // ------------------
    // REALTOR
    // ------------------

    // --- GET ---

    fun getRealtorCurrent() : MutableLiveData<Realtor> = realtorRepository.getCurrentRealtor()

}
