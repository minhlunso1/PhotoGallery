package minhna.android.photogallery.model

import com.squareup.moshi.Json

data class LocationEntity(

	@field:Json(name="country")
	val country: String? = null,

	@field:Json(name="city")
	val city: String? = null,

	@field:Json(name="position")
	val position: Coordination? = null
)