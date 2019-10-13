package minhna.android.photogallery.model

import com.squareup.moshi.Json

data class ExifEntity(

	@field:Json(name="exposure_time")
	val exposureTime: String? = null,

	@field:Json(name="aperture")
	val aperture: String? = null,

	@field:Json(name="focal_length")
	val focalLength: String? = null,

	@field:Json(name="iso")
	val iso: Int? = null,

	@field:Json(name="model")
	val model: String? = null,

	@field:Json(name="make")
	val make: String? = null
)