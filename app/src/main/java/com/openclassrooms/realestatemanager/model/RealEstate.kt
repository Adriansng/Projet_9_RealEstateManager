package com.openclassrooms.realestatemanager.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Real_Estate")
data class RealEstate(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id") val id: Long,
        @ColumnInfo(name = "price") val price: Double,
        @ColumnInfo(name = "area") val area: Long,
        @ColumnInfo(name = "type") val type: String?,
        @ColumnInfo(name = "numberRoom") val numberRoom: Long,
        @ColumnInfo(name = "numberBedroom") val numberBedroom: Long,
        @ColumnInfo(name = "numberBathroom") val numberBathroom: Long,
        @ColumnInfo(name = "descriptionRealEstate") val descriptionRealEstate: String?,
        @ColumnInfo(name = "location") val location: String?,
        @ColumnInfo(name = "closeToSchool") val closeToSchool: Boolean,
        @ColumnInfo(name = "closeToCommerce") val closeToCommerce: Boolean,
        @ColumnInfo(name = "closeToPark") val closeToPark: Boolean,
        @ColumnInfo(name = "address") val address: String?,
        @ColumnInfo(name = "zipCode") val zipCode: String?,
        @ColumnInfo(name = "city") val city: String?,
        @ColumnInfo(name = "country") val country: String?,
        @ColumnInfo(name = "creationDate") val creationDate: Long,
        @ColumnInfo(name = "saleCreation") val saleCreation: Long?,
        @ForeignKey(entity = Realtor::class, parentColumns = ["id"], childColumns = ["id"])
        @ColumnInfo(name = "idRealtor") val idRealtor: Long)
        :Serializable{
}