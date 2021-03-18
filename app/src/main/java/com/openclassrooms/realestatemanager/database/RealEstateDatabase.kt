package com.openclassrooms.realestatemanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.openclassrooms.realestatemanager.database.dao.PhotoDao
import com.openclassrooms.realestatemanager.database.dao.RealEstateDao
import com.openclassrooms.realestatemanager.database.dao.RealtorDao
import com.openclassrooms.realestatemanager.model.Photo
import com.openclassrooms.realestatemanager.model.RealEstate
import com.openclassrooms.realestatemanager.model.Realtor

@Database(entities = [Photo::class, RealEstate::class, Realtor::class],
        version = 1)
abstract class RealEstateDatabase : RoomDatabase() {
    abstract fun realEstateDao() : RealEstateDao
    abstract fun realtorDao() : RealtorDao
    abstract fun photoDao(): PhotoDao

    companion object {
        @Volatile
        private var INSTANCE: RealEstateDatabase? = null

        fun createInMemoryDatabase(context: Context) {
            INSTANCE = Room.inMemoryDatabaseBuilder(context, RealEstateDatabase::class.java)
                    .allowMainThreadQueries()
                    .build()
        }

        fun getDatabase(context: Context): RealEstateDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        RealEstateDatabase::class.java,
                        "realty_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}