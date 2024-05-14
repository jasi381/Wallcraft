package com.jasmeet.wallcraft.model.apiService

import com.jasmeet.wallcraft.BuildConfig
import com.jasmeet.wallcraft.model.OrderBy
import com.jasmeet.wallcraft.model.apiResponse.remote.detailsApiResponse.DetailsApiResponse
import com.jasmeet.wallcraft.model.apiResponse.remote.homeApiResponse.HomeApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    companion object {
        const val BASE_URL = "https://api.unsplash.com/"
        const val CLIENT_ID = BuildConfig.client_id
    }


    @GET("photos")
    suspend fun getHomeScreenData(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 20,
        @Query("order_by") orderBy: String = OrderBy.LATEST.displayName,
        @Query("client_id") clientId: String = CLIENT_ID,
    ): List<HomeApiResponse>

    @GET("photos/{id}")
    suspend fun getWallpaperDetails(
        @Path("id") id: String,
        @Query("client_id") clientId: String = CLIENT_ID,
    ): DetailsApiResponse


}