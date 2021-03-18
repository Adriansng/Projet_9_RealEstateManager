package com.openclassrooms.realestatemanager.di.module

import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import com.openclassrooms.realestatemanager.repositories.RealtorRepository
import org.koin.dsl.module

/**
 * Created by Adrian SENEGAS 16/03/2021.
 */

    val repoModule = module {
        single { RealEstateRepository(get()) }
        single { RealtorRepository(get()) }
    }