package com.openclassrooms.realestatemanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Created by Adrian SENEGAS 18/03/2021.
 */
@Entity(tableName = "Photo")
data class Photo(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id") val idPhoto: Long,
        @ColumnInfo(name = "uri") var uri: String,
        @ColumnInfo(name = "descriptionPhoto") var descriptionPhoto: String,
        @ForeignKey(entity = RealEstate::class, parentColumns = ["id"], childColumns = ["id"])
        @ColumnInfo(name = "idRealEstate") var idRealEstate: Long
        ){
        companion object{
                fun default() = Photo(idPhoto = 0,
                                    uri = "",
                                   descriptionPhoto = "",
                                   idRealEstate = 0)}
        }

