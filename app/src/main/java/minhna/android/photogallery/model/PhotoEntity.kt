package minhna.android.photogallery.model

import com.squareup.moshi.Json

data class PhotoEntity (
    @field:Json(name="urls")
    val urls: PhotoUrl = PhotoUrl(),

    @field:Json(name="updated_at")
    val updatedAt: String? = null,

    @field:Json(name="color")
    val color: String? = null,

    @field:Json(name="width")
    val width: Int? = null,

    @field:Json(name="created_at")
    val createdAt: String? = null,

    @field:Json(name="description")
    val description: String? = null,

    @field:Json(name="id")
    val id: String? = null,

    @field:Json(name="liked_by_user")
    val likedByUser: Boolean? = null,

    @field:Json(name="height")
    val height: Int? = null,

    @field:Json(name="likes")
    val likes: Int? = null,

    @field:Json(name="location")
    val location: LocationEntity? = null,

    @field:Json(name="exif")
    val exif: ExifEntity? = null
)