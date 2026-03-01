package com.weigner.marvel.framework.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.weigner.core.data.repository.EpisodesRemoteDataSource
import com.weigner.core.domain.model.Episode

class CharactersPagingSource(
    private val remoteDataSource: EpisodesRemoteDataSource,
) : PagingSource<Int, Episode>() {

    @Suppress("TooGenericExceptionCaught")
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Episode> {
        return try {
            val nextPageNumber = params.key ?: 1
            val queries = hashMapOf(
                "page" to nextPageNumber
            )
            val episodePaging = remoteDataSource.fetchEpisodes(queries)

            val nextPage = episodePaging.nextPage.substringAfter("page=").toInt()
            val prevPage = episodePaging.previousPage?.substringAfter("page=")?.toInt()

            LoadResult.Page(
                data = episodePaging.episodes,
                prevKey = prevPage,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Episode>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(LIMIT) ?: anchorPage?.nextKey?.minus(LIMIT)
        }
    }

    companion object {
        private const val LIMIT = 20
    }
}