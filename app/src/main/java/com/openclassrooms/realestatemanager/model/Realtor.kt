package com.openclassrooms.realestatemanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "Realtor")
data class Realtor(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id") val id: Long,
        @ColumnInfo(name = "name") var name: String,
        @ColumnInfo(name = "prefEuro") var prefEuro: Boolean
        ):Serializable
{
        companion object {
                fun default() = Realtor(id = 0, name = "", prefEuro = false)
        }

}
