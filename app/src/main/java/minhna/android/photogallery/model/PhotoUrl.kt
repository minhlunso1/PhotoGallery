package minhna.android.photogallery.model

import com.squareup.moshi.Json

const val defaultUrl = "https://cdn.dribbble.com/users/1274627/screenshots/3390285/404-error-sad-boat-800x600.jpg"

data class PhotoUrl(

	@field:Json(name="small")
	val small: String = defaultUrl,

	@field:Json(name="thumb")
	val thumb: String = defaultUrl,

	@field:Json(name="raw")
	val raw: String = defaultUrl,

	@field:Json(name="regular")
	val regular: String = defaultUrl,

	@field:Json(name="full")
	val full: String = defaultUrl
)