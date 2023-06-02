package com.example.newsapplication.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil.network.HttpException
import com.example.newsapplication.data.remote.PostService
import com.example.newsapplication.ui.models.UiNews
import java.io.IOException

class NewsPagingSource(
    private val postService: PostService,
    private val query: String,
    private val sectionFilter: String,
    private val typeFilter: String
) : PagingSource<Int, UiNews>() {

    override fun getRefreshKey(state: PagingState<Int, UiNews>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UiNews> {
        return try {
            val pageNumber = params.key ?: 1
            val response =
                postService.getSearchByQuery(query, pageNumber, sectionFilter, typeFilter)
            val responseData = response.response.results?.map { it.mapToUiModel() }
                ?: emptyList()
            LoadResult.Page(
                data = responseData,
                null,
                nextKey = if (responseData.isNotEmpty()) pageNumber + 1 else null
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}
