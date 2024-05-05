package com.jasmeet.wallcraft.model.apiService

import com.jasmeet.wallcraft.BuildConfig
import com.jasmeet.wallcraft.model.apiResponse.remote.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    companion object {
        const val BASE_URL = "https://api.pexels.com/v1/"
        const val AUTH_TOKEN = BuildConfig.auth_token
    }


    @GET("search?query=hdWallpapers&per_page=50")
    suspend fun getHomeScreenData(
        @Query("page") page: Int,
        @Header("Authorization") auth_token: String = AUTH_TOKEN
    ): ApiResponse
}