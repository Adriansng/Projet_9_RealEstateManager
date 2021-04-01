package com.openclassrooms.realestatemanager.model

import androidx.room.Embedded
import androidx.room.Relation

data class RealEstateComplete(
        @Embedded val realEstate: RealEstate,
        @Relation(
                parentColumn = "realEstate_id",
                entityColumn = "realEstate_id"
        )
        val photos: List<Photo>
)