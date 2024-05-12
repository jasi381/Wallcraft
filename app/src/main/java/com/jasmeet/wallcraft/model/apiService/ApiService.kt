package com.jasmeet.wallcraft.model.apiService

import androidx.paging.PagingData
import com.jasmeet.wallcraft.BuildConfig
import com.jasmeet.wallcraft.model.apiResponse.remote.ApiResponse
import com.jasmeet.wallcraft.model.apiResponse.remote.homeApiResponse.HomeApiResponse
import com.jasmeet.wallcraft.model.apiResponse.remote.homeApiResponse.Urls
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    companion object {
        const val BASE_URL = "https://api.unsplash.com/"
        const val CLIENT_ID = BuildConfig.client_id
    }


    @GET("photos/?client_id=$CLIENT_ID&order_by=latest&per_page=20")
    suspend fun getHomeScreenData(
        @Query("page") page: Int,
    ): List<HomeApiResponse>


    @GET("search?per_page=50")
    suspend fun getSearchScreenData(
        @Query("page") page: Int,
        @Header("Authorization") auth_token: String = CLIENT_ID,
        @Query("query") query: String
    ): ApiResponse
}