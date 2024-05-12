package com.jasmeet.wallcraft.model.repo

import androidx.paging.PagingData
import com.jasmeet.wallcraft.model.apiResponse.remote.homeApiResponse.HomeApiResponse
import com.jasmeet.wallcraft.model.apiResponse.remote.homeApiResponse.Urls
import kotlinx.coroutines.flow.Flow

interface HomeRepo {
    suspend fun getHomeData(page: Int): List<HomeApiResponse>
}