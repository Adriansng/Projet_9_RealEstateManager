package com.openclassrooms.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.openclassrooms.realestatemanager.model.RealEstate

@Dao
interface RealEstateDao {
    @Query("SELECT * FROM Real_Estate")
    fun getRealEstates(): LiveData<List<RealEstate>>

    @Query("SELECT * FROM Real_Estate WHERE id = :id")
    fun getRealEstate(id: Long): RealEstate

    @Query("SELECT seq FROM sqlite_sequence WHERE name = :tableName")
    fun getRealEstateLast(tableName: String): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRealEstates(vararg realEstates: RealEstate)

    @Update
    fun updateRealEstate(realEstate: RealEstate)


}