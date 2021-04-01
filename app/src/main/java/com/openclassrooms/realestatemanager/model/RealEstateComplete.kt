package com.openclassrooms.realestatemanager.model

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Created by Adrian SENEGAS 01/04/2021.
 */
data class RealEstateComplete(
        @Embedded val realEstate: RealEstate,
        @Relation(
                parentColumn = "realEstate_id",
                entityColumn = "realEstate_id"
        )
        val photos: List<Photo>
)