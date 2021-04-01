package com.openclassrooms.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.openclassrooms.realestatemanager.model.RealEstate
import com.openclassrooms.realestatemanager.model.RealEstateComplete

@Dao
interface RealEstateDao {

    @Transaction
    @Query("SELECT * FROM Real_Estate")
    fun getRealEstates(): LiveData<List<RealEstateComplete>>

    @Transaction
    @Query("SELECT * FROM Real_Estate WHERE realEstate_id = :id")
    fun getRealEstate(id: Long): RealEstateComplete

    @Query("SELECT seq FROM sqlite_sequence WHERE name = :tableName")
    fun getRealEstateLast(tableName: String): Long?

    @RawQuery(observedEntities = [RealEstate::class])
    fun getItemsBySearch(query: SupportSQLiteQuery) : LiveData<List<RealEstateComplete>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRealEstates(realEstates: RealEstate)



}