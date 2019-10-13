package minhna.android.photogallery.model

import com.squareup.moshi.Json

data class LocationEntity(

	@field:Json(name="country")
	val country: String? = null,

	@field:Json(name="city")
	val city: String? = null,

	@field:Json(name="position")
	val position: Coordination? = null
) {
	fun displayPlace():String {
		var place = ""
		if (!city.isNullOrEmpty())
			place = city

		if (!country.isNullOrEmpty()) {
			if (country != city) {
				if (place.isEmpty())
					place = country
				else
					place += ", $country"
			}
		}

		return place
	}
}