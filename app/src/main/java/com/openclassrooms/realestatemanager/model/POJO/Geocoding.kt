package com.openclassrooms.realestatemanager.model.POJO

import com.google.gson.annotations.SerializedName



data class Geocoding (

		@SerializedName("results") val results : List<Results>,
		@SerializedName("status") val status : String,
		@SerializedName("error_message") val errorMessage : String
)