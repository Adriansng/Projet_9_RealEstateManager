package com.openclassrooms.realestatemanager.di.module

import androidx.room.Room
import com.openclassrooms.realestatemanager.database.RealEstateDatabase
import com.openclassrooms.realestatemanager.repositories.PhotoRepository
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import com.openclassrooms.realestatemanager.repositories.RealtorRepository
import com.openclassrooms.realestatemanager.viewModel.*
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        RealEstateDatabase.buildDatabase(get())
    }
    single { get<RealEstateDatabase>().realEstateDao() }
    single { get<RealEstateDatabase>().realtorDao() }
    single { get<RealEstateDatabase>().photoDao() }
    single { RealEstateRepository(get()) }
    single { RealtorRepository(get()) }
    single { PhotoRepository(get()) }
}

// ----------------------
// VIEW MODEL
// ----------------------


val itemListModule = module {
    viewModel {
        ItemListViewModel(get(), get())
    }
}

val itemDetailModule = module {
    viewModel {
        ItemDetailFragmentViewModel(get(), get(),get())
    }
}
val mapModule = module {
    viewModel {
        MapViewModel(get(), get())
    }
}

val itemFilterModule = module {
    viewModel {
        SearchViewModel(get(), get())
    }
}


val simulatorModule = module {
    viewModel {
        SimulatorLoanViewModel(get())
    }
}

val itemCreationModule = module {
    viewModel {
        ItemCreationViewModel(get(), get(), get())
    }
}