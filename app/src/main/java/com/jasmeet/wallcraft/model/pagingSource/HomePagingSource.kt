package com.jasmeet.wallcraft.model.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jasmeet.wallcraft.model.apiResponse.remote.homeApiResponse.Urls
import com.jasmeet.wallcraft.model.repo.HomeRepo
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class HomePagingSource @Inject constructor(
    private val homeRepo: HomeRepo
) : PagingSource<Int, Urls>() {
    override fun getRefreshKey(state: PagingState<Int, Urls>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Urls> {
        val page = params.key ?: 1

        val response = homeRepo.getHomeData(page = page).map {
            it.urls
        }


        return try {
            LoadResult.Page(
                data = response,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (e: IOException) {
            // Network IO exceptions
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HTTP exceptions
            if (e.code() == 525) {
                // Handle HTTP 525 error
                return LoadResult.Error(e)
            } else {
                // Handle other HTTP exceptions
                return LoadResult.Error(e)
            }
        } catch (e: Exception) {
            // Catch any other exceptions
            return LoadResult.Error(e)
        }
    }

}