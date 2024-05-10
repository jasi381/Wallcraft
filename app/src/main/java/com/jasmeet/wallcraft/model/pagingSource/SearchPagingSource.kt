package com.jasmeet.wallcraft.model.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jasmeet.wallcraft.model.apiResponse.remote.Photo
import com.jasmeet.wallcraft.model.repo.SearchRepo
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchPagingSource @Inject constructor(
    private val searchRepo: SearchRepo,
    private val query: String
) : PagingSource<Int, Photo>() {
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val page = params.key ?: 1

        val response = searchRepo.getSearchedWallpapers(page = page, query = query)

        return try {
            if (response.photos.isEmpty()) {
                // If there are no photos, indicate that there are no more pages
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = null
                )
            } else {
                // If there are photos, return the page with nextKey
                LoadResult.Page(
                    data = response.photos,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (response.next_page != null) page + 1 else null
                )
            }
        } catch (e: IOException) {
            // Network IO exceptions
            LoadResult.Error(e)
        } catch (e: HttpException) {
            // HTTP exceptions
            LoadResult.Error(e)
        } catch (e: Exception) {
            // Catch any other exceptions
            LoadResult.Error(e)
        }
    }


}