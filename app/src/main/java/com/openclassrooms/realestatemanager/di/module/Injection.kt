package com.openclassrooms.realestatemanager.di.module

import androidx.room.Room
import com.openclassrooms.realestatemanager.database.RealEstateDatabase
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import com.openclassrooms.realestatemanager.repositories.RealtorRepository
import com.openclassrooms.realestatemanager.viewModel.ItemCreationViewModel
import com.openclassrooms.realestatemanager.viewModel.ItemListViewModel
import com.openclassrooms.realestatemanager.viewModel.SimulatorLoanViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
                get(),
                RealEstateDatabase::class.java, "rem_database"
        ).build()
    }
    single { get<RealEstateDatabase>().realEstateDao() }
    single { get<RealEstateDatabase>().realtorDao() }
    single { get<RealEstateDatabase>().photoDao() }
    single { RealEstateRepository(get()) }
    single { RealtorRepository(get()) }
}

// ----------------------
// VIEW MODEL
// ----------------------

val itemListModule = module {
    viewModel {
        ItemListViewModel(get(), get())
    }
}

val simulatorModule = module {
    viewModel {
        SimulatorLoanViewModel(get())
    }
}

val itemCreationModule = module {
    viewModel {
        ItemCreationViewModel(get())
    }
}