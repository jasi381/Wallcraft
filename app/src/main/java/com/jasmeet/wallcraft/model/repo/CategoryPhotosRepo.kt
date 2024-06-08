package com.jasmeet.wallcraft.model.repo

import com.jasmeet.wallcraft.model.apiResponse.remote.categoryDetailsApiResponse.CategoryDetailApiResponse

interface CategoryPhotosRepo {
    suspend fun getCategoryDetails(page: Int, orderBy: String): List<CategoryDetailApiResponse>
}