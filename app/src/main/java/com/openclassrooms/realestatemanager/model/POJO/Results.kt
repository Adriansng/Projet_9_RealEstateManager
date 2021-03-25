package com.openclassrooms.realestatemanager.model.POJO

import com.google.gson.annotations.SerializedName


data class Results (

		@SerializedName("address_components") val address_components : List<Address_components>,
		@SerializedName("formatted_address") val formatted_address : String,
		@SerializedName("geometry") val geometry : Geometry,
		@SerializedName("place_id") val place_id : String,
		@SerializedName("plus_code") val plus_code : Plus_code,
		@SerializedName("types") val types : List<String>
)