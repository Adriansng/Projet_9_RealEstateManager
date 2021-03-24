package com.openclassrooms.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.openclassrooms.realestatemanager.model.Photo


@Dao
interface PhotoDao {
    @Query("SELECT * FROM Photo WHERE idRealEstate = :id")
    fun getPhotos(id: Long): LiveData<List<Photo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhotos(vararg photos: Photo)

    @Delete
    fun deletePhoto(photo: Photo)
}