package com.openclassrooms.realestatemanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Photo")
data class Photo(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "photo_id") var idPhoto: Long,
        @ColumnInfo(name = "uri") var uri: String,
        @ColumnInfo(name = "descriptionPhoto") var descriptionPhoto: String,
        @ForeignKey(entity = RealEstate::class, parentColumns = ["realEstate_id"], childColumns = ["realEstate_id"])
        @ColumnInfo(name = "realEstate_id") var idRealEstate: Long
        )
{
        companion object {
                fun default() = Photo(
                        idPhoto = 0,
                        uri = "",
                        descriptionPhoto = "",
                        idRealEstate = 0,
                )
        }
}