package minhna.android.photogallery.remote.api

import minhna.android.photogallery.app.Const
import minhna.android.photogallery.model.PhotoEntity
import minhna.android.photogallery.remote.pojo.PhotoSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("photos")
    suspend fun getPhotoList(
        @Query("per_page") perPage: Int = Const.PER_PAGE,
        @Query("page") page: Int
    ): Response<List<PhotoEntity>>

    @GET("search/photos")
    suspend fun search(
        @Query("query") text: String? = null,
        @Query("per_page") perPage: Int = Const.PER_PAGE,
        @Query("page") page: Int
    ): Response<PhotoSearchResponse>
}