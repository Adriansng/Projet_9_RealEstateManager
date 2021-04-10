package com.openclassrooms.realestatemanager.repositories

import com.openclassrooms.realestatemanager.database.dao.PhotoDao
import com.openclassrooms.realestatemanager.model.Photo

class PhotoRepository(
        private val photoDao: PhotoDao) {

    // ------------------
    // PHOTO
    // ------------------

    // --- INSERT ---

    fun insertPhoto(photo: Photo) = photoDao.insertPhotos(photo)

    // --- DELETE ---

    fun deleteAll(id: Long) = photoDao.deleteAll(id)

}