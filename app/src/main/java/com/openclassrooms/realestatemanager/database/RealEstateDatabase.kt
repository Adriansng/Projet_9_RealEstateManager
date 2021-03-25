package com.openclassrooms.realestatemanager.database

import android.content.Context
import android.media.CamcorderProfile
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.openclassrooms.realestatemanager.database.dao.PhotoDao
import com.openclassrooms.realestatemanager.database.dao.RealEstateDao
import com.openclassrooms.realestatemanager.database.dao.RealtorDao
import com.openclassrooms.realestatemanager.model.Photo
import com.openclassrooms.realestatemanager.model.RealEstate
import com.openclassrooms.realestatemanager.model.Realtor
import com.openclassrooms.realestatemanager.utils.Converters
import kotlin.concurrent.thread

@Database(entities = [Photo::class, RealEstate::class, Realtor::class],
        version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RealEstateDatabase : RoomDatabase() {
    abstract fun realEstateDao() : RealEstateDao
    abstract fun realtorDao() : RealtorDao
    abstract fun photoDao(): PhotoDao

    companion object {
        @Volatile
        private var INSTANCE: RealEstateDatabase? = null

        fun buildDatabase(context: Context) : RealEstateDatabase? {
            INSTANCE?.let { return it} ?: synchronized(RealEstateDatabase::class.java){
                INSTANCE?.let{return it}
                Room.databaseBuilder(context.applicationContext, RealEstateDatabase::class.java,
                        "rem_database")
                        .addCallback(object : Callback(){
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                thread { buildDatabase(context)?.realtorDao()?.insertRealtors(Realtor(1,"John",false)) }
                            }
                        })
                        .allowMainThreadQueries()
                        .build().also { INSTANCE = it }
            }
            return INSTANCE
        }

        
    }
}