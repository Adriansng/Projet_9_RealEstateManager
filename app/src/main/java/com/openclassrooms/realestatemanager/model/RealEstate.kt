package com.openclassrooms.realestatemanager.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

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
        :Parcelable {
        constructor(parcel: Parcel) : this(
                id = parcel.readLong(),
                price = parcel.readDouble(),
                area = parcel.readLong(),
                type = parcel.readString(),
                numberRoom = parcel.readLong(),
                numberBedroom = parcel.readLong(),
                numberBathroom = parcel.readLong(),
                descriptionRealEstate = parcel.readString(),
                location = parcel.readString(),
                closeToSchool = parcel.readByte() != 0.toByte(),
                closeToCommerce = parcel.readByte() != 0.toByte(),
                closeToPark = parcel.readByte() != 0.toByte(),
                address = parcel.readString(),
                zipCode = parcel.readString(),
                city = parcel.readString(),
                country = parcel.readString(),
                creationDate = parcel.readLong(),
                saleCreation = parcel.readLong(),
                idRealtor = parcel.readLong())

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeLong(id)
                parcel.writeDouble(price)
                parcel.writeLong(area)
                parcel.writeString(type)
                parcel.writeLong(numberRoom)
                parcel.writeLong(numberBedroom)
                parcel.writeLong(numberBathroom)
                parcel.writeString(descriptionRealEstate)
                parcel.writeString(location)
                parcel.writeByte(if (closeToSchool) 1 else 0)
                parcel.writeByte(if (closeToCommerce) 1 else 0)
                parcel.writeByte(if (closeToPark) 1 else 0)
                parcel.writeString(address)
                parcel.writeString(zipCode)
                parcel.writeString(city)
                parcel.writeString(country)
                parcel.writeLong(creationDate)
                if (saleCreation != null) parcel.writeLong(saleCreation)
                parcel.writeLong(idRealtor)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<RealEstate> {
                override fun createFromParcel(parcel: Parcel): RealEstate {
                        return RealEstate(parcel)
                }

                override fun newArray(size: Int): Array<RealEstate?> {
                        return arrayOfNulls(size)
                }
        }
}