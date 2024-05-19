package com.jasmeet.wallcraft.model.repo

import com.jasmeet.wallcraft.model.apiResponse.remote.categoriesApiResponse.CategoriesApiResponse

interface CategoriesRepo {
    suspend fun getCategories(): List<CategoriesApiResponse>
}