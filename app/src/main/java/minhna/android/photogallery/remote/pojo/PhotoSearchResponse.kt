package minhna.android.photogallery.remote.pojo

import minhna.android.photogallery.model.PhotoEntity

data class PhotoSearchResponse(
    val results: List<PhotoEntity> = ArrayList()
)