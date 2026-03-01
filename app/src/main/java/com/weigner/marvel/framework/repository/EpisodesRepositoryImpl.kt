package com.weigner.marvel.framework.repository

import androidx.paging.PagingSource
import com.weigner.core.data.repository.EpisodesRemoteDataSource
import com.weigner.core.data.repository.EpisodesRepository
import com.weigner.core.domain.model.Episode
import com.weigner.marvel.framework.paging.CharactersPagingSource
import javax.inject.Inject

class EpisodesRepositoryImpl @Inject constructor(
    private val remoteDataSource: EpisodesRemoteDataSource
) : EpisodesRepository{

    override fun getEpisodes(): PagingSource<Int, Episode> {
        return CharactersPagingSource(remoteDataSource)
    }

    override suspend fun getEpisode(episodeId: Int): Episode {
        return remoteDataSource.fetchEpisodeById(episodeId)
    }

    override suspend fun getEpisodes(episodesIds: List<Int>): List<Episode> {
        return remoteDataSource.fetchEpisodes(episodesIds)
    }
}