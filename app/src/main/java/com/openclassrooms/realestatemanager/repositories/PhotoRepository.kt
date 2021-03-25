package com.openclassrooms.realestatemanager.repositories

import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.database.dao.PhotoDao
import com.openclassrooms.realestatemanager.model.Photo

class PhotoRepository(
        private val photoDao: PhotoDao) {

    fun getListPhoto(idRealEstate: Long): LiveData<List<Photo>> = photoDao.getPhotos(idRealEstate)
    fun insertPhoto(photo: Photo) = photoDao.insertPhotos(photo)
    fun deletePhoto(photo: Photo) = photoDao.deletePhoto(photo)
    fun deleteAll(id: Long) = photoDao.deleteAll(id)

}