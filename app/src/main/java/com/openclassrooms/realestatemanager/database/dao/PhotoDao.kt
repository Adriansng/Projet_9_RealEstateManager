package com.openclassrooms.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.openclassrooms.realestatemanager.model.Photo


@Dao
interface PhotoDao {
    @Query("SELECT * FROM Photo")
    fun getPhotos(): LiveData<List<Photo>>

    @Insert
    fun insertPhotos(vararg photos: Photo)

    @Update
    fun updatePhoto(photo: Photo)

    @Delete
    fun deletePhoto(photo: Photo)
}