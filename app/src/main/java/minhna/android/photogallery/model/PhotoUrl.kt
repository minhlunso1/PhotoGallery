package minhna.android.photogallery.model

import com.squareup.moshi.Json

const val defaultUrl = "https://cdn.dribbble.com/users/1274627/screenshots/3390285/404-error-sad-boat-800x600.jpg"

data class PhotoUrl(

	@field:Json(name="small")
	private val small: String? = defaultUrl,

	@field:Json(name="thumb")
	private val thumb: String? = defaultUrl,

	@field:Json(name="raw")
	private val raw: String? = defaultUrl,

	@field:Json(name="regular")
	private val regular: String? = defaultUrl,

	@field:Json(name="full")
	private val full: String? = defaultUrl
) {
	fun getSmall() = small ?: defaultUrl
	fun getThumb() = thumb ?: defaultUrl
	fun getRaw() = raw ?: defaultUrl
	fun getRegular() = regular ?: defaultUrl
	fun getFull() = full ?: defaultUrl
}