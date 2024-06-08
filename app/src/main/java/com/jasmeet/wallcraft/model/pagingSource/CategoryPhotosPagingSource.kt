package com.jasmeet.wallcraft.model.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jasmeet.wallcraft.model.OrderBy
import com.jasmeet.wallcraft.model.apiResponse.remote.categoryDetailsApiResponse.CategoryDetailApiResponse
import com.jasmeet.wallcraft.model.repo.CategoryPhotosRepo
import javax.inject.Inject


class CategoryPhotosPagingSource @Inject constructor(
    private val categoryPhotosRepo: CategoryPhotosRepo,
    private val orderBy: String = OrderBy.LATEST.displayName
) : PagingSource<Int, CategoryDetailApiResponse>() {

    override fun getRefreshKey(state: PagingState<Int, CategoryDetailApiResponse>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CategoryDetailApiResponse> {

        val page = params.key ?: 1
        val response = categoryPhotosRepo.getCategoryDetails(page = page, orderBy = orderBy)

        return try {
            LoadResult.Page(
                data = response,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}