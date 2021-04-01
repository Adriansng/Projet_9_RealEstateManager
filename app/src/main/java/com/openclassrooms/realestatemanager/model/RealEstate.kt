package com.openclassrooms.realestatemanager.model

import android.content.ContentValues
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.utils.Converters
import java.io.Serializable
import java.util.*

@Entity(tableName = "Real_Estate")
data class RealEstate(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "realEstate_id") val id: Long,
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
        @ColumnInfo(name = "saleCreation") var saleDate: Long?,
        @ColumnInfo(name = "isSold") var isSold: Boolean,
        @ForeignKey(entity = Realtor::class, parentColumns = ["id"], childColumns = ["id"])
        @ColumnInfo(name = "idRealtor") var idRealtor: Long
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
                        saleDate = null,
                        isSold = false,
                        idRealtor = 1
                        )
        }

        fun fromContentValues(values: ContentValues) : RealEstate{
                val realEstate = RealEstate.default()
                if (values.containsKey("type")) realEstate.type = values.getAsString("type")
                if (values.containsKey("price")) realEstate.price = values.getAsInteger("price")
                if (values.containsKey("surface")) realEstate.area = values.getAsInteger("surface")
                if (values.containsKey("roomNumber")) realEstate.numberRoom = values.getAsInteger("roomNumber")
                if (values.containsKey("bathroomNumber")) realEstate.numberBathroom = values.getAsInteger("bathroomNumber")
                if (values.containsKey("bedroomNumber")) realEstate.numberBedroom = values.getAsInteger("bedroomNumber")
                if (values.containsKey("desc")) realEstate.descriptionRealEstate = values.getAsString("description")
                if (values.containsKey("location")) realEstate.location = Converters().deserializeLatLng(values.getAsString("location"))
                if (values.containsKey("address")) realEstate.address = values.getAsString("address")
                if (values.containsKey("city")) realEstate.city = values.getAsString("city")
                if (values.containsKey("zip")) realEstate.zipCode = values.getAsString("zip")
                if (values.containsKey("parks")) realEstate.closeToPark = values.getAsBoolean("parks")
                if (values.containsKey("commerce")) realEstate.closeToCommerce = values.getAsBoolean("commerce")
                if (values.containsKey("schools")) realEstate.closeToSchool = values.getAsBoolean("schools")
                if (values.containsKey("creationDate")) realEstate.creationDate = values.getAsLong("creationDate")
                if (values.containsKey("isSold")) realEstate.isSold = values.getAsBoolean("isSold")
                if (values.containsKey("soldDate")) if (values.getAsLong("soldDate") == null) realEstate.saleDate = null else realEstate.saleDate = values.getAsLong("soldDate")
                if (values.containsKey("estateAgent")) realEstate.idRealtor = values.getAsLong("estateAgent")
                return realEstate
        }


}