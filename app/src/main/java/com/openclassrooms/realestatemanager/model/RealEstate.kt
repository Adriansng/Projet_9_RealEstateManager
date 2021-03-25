package com.openclassrooms.realestatemanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import java.io.Serializable
import java.util.*

@Entity(tableName = "Real_Estate")
data class RealEstate(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id") val id: Long,
        @ColumnInfo(name = "price") var price: Int,
        @ColumnInfo(name = "area") var area: Int,
        @ColumnInfo(name = "type") var type: String?,
        @ColumnInfo(name = "numberRoom") var numberRoom: Int,
        @ColumnInfo(name = "numberBedroom") var numberBedroom: Int,
        @ColumnInfo(name = "numberBathroom") var numberBathroom: Int,
        @ColumnInfo(name = "descriptionRealEstate") var descriptionRealEstate: String?,
        @ColumnInfo(name = "location") var location: LatLng?,
        @ColumnInfo(name = "closeToSchool") var closeToSchool: Boolean,
        @ColumnInfo(name = "closeToCommerce") var closeToCommerce: Boolean,
        @ColumnInfo(name = "closeToPark") var closeToPark: Boolean,
        @ColumnInfo(name = "address") var address: String?,
        @ColumnInfo(name = "zipCode") var zipCode: String?,
        @ColumnInfo(name = "city") var city: String?,
        @ColumnInfo(name = "creationDate") var creationDate: Long,
        @ColumnInfo(name = "saleCreation") var saleCreation: Long?,
        @ForeignKey(entity = Realtor::class, parentColumns = ["id"], childColumns = ["id"])
        @ColumnInfo(name = "idRealtor") var idRealtor: Long,
        @ColumnInfo(name = "photo") var photo: String?
        ):Serializable
{
        companion object {
                fun default() = RealEstate(
                        id = 0,
                        price = 0 ,
                        area = 0,
                        type = "",
                        numberRoom = 0,
                        numberBedroom = 0,
                        numberBathroom = 0,
                        descriptionRealEstate = "",
                        location = null,
                        closeToSchool = false,
                        closeToCommerce = false,
                        closeToPark = false,
                        address = "",
                        zipCode = "",
                        city = "",
                        creationDate = Date().time,
                        saleCreation = null,
                        idRealtor = 1,
                        photo = ""
                        )
        }


}