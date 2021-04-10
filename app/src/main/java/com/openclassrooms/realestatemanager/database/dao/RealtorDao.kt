package com.openclassrooms.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.openclassrooms.realestatemanager.model.Realtor


@Dao
interface RealtorDao {
    @Query("SELECT * FROM Realtor")
    fun getRealtors(): LiveData<List<Realtor>>

    @Query("SELECT * FROM Realtor WHERE id = :id")
    fun getRealtor(id: Long): LiveData<Realtor>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRealtors(vararg realtors: Realtor)

    @Update
    fun updateRealtor(realtor: Realtor)

}