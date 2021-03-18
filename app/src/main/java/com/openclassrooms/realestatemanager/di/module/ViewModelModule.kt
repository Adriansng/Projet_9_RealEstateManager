package com.openclassrooms.realestatemanager.di.module

import com.openclassrooms.realestatemanager.viewModel.ItemListViewModel
import com.openclassrooms.realestatemanager.viewModel.SimulatorLoanViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Adrian SENEGAS 16/03/2021.
 */
    val viewModelModule = module {
        viewModel{
            SimulatorLoanViewModel(get())
            ItemListViewModel(get(),get())
        }
    }