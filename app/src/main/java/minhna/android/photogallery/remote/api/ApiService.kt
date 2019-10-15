package minhna.android.photogallery.remote.api

import minhna.android.photogallery.model.Const
import minhna.android.photogallery.model.PhotoEntity
import minhna.android.photogallery.remote.pojo.PhotoSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    /**
     * @param perPage Number of items per page.
     * @param page Page number to retrieve.
     * @return Get a single page from the list of all photos.
     */
    @GET("photos")
    suspend fun getPhotoList(
        @Query("per_page") perPage: Int = Const.PER_PAGE,
        @Query("page") page: Int
    ): Response<List<PhotoEntity>>

    /**
     * @param id The id of photo.
     * @return Retrieve a single photo.
     */
    @GET("photos/{id}")
    suspend fun getPhotoDetails(
        @Path("id") id: String
    ): Response<PhotoEntity>

    /**
     * @param query Search terms.
     * @param perPage Number of items per page.
     * @param page Page number to retrieve.
     * @return Get a single page of photo results for a query.
     */
    @GET("search/photos")
    suspend fun search(
        @Query("query") text: String? = null,
        @Query("per_page") perPage: Int = Const.PER_PAGE,
        @Query("page") page: Int
    ): Response<PhotoSearchResponse>
}