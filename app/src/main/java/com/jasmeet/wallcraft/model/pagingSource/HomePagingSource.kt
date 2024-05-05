package com.jasmeet.wallcraft.model.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jasmeet.wallcraft.model.apiResponse.remote.Photo
import com.jasmeet.wallcraft.model.repo.HomeRepo
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class HomePagingSource @Inject constructor(
    private val homeRepo: HomeRepo
) : PagingSource<Int, Photo>() {
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val page = params.key ?: 1

        val response = homeRepo.getHomeData(page = page)


        return try {
            LoadResult.Page(
                data = response.photos,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.photos.isEmpty()) null else page + 1
            )
        } catch (e: IOException) {
            // Network IO exceptions
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HTTP exceptions
            return LoadResult.Error(e)
        } catch (e: Exception) {
            // Catch any other exceptions
            return LoadResult.Error(e)
        }
    }

}