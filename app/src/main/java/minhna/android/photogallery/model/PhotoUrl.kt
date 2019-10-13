package minhna.android.photogallery.model

import com.squareup.moshi.Json

data class PhotoUrl(

	@field:Json(name="small")
	val small: String? = null,

	@field:Json(name="thumb")
	val thumb: String? = null,

	@field:Json(name="raw")
	val raw: String? = null,

	@field:Json(name="regular")
	val regular: String? = null,

	@field:Json(name="full")
	val full: String? = null
)