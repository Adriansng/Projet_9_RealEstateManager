package com.openclassrooms.realestatemanager.model.POJO

import com.google.gson.annotations.SerializedName



data class Address_components (

		@SerializedName("long_name") val long_name : String,
		@SerializedName("short_name") val short_name : String,
		@SerializedName("types") val types : List<String>
)