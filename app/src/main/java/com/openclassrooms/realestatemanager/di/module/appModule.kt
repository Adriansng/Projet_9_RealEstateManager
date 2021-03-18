package com.openclassrooms.realestatemanager.di.module

import androidx.room.Room
import com.openclassrooms.realestatemanager.database.RealEstateDatabase
import org.koin.dsl.module

/**
 * Created by Adrian SENEGAS 16/03/2021.
 */
val appModule = module {
    single {
        Room.databaseBuilder(
                get(),
                RealEstateDatabase::class.java, "rem_database"
        ).build()
    }
}