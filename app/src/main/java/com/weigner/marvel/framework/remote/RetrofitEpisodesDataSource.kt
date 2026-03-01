package com.weigner.marvel.framework.remote

import com.weigner.core.data.repository.EpisodesRemoteDataSource

import com.weigner.core.domain.model.Episode
import com.weigner.core.domain.model.EpisodePaging
import com.weigner.marvel.framework.network.RickAndMortyApi
import com.weigner.marvel.framework.network.response.toEpisodeModel
import javax.inject.Inject

class RetrofitEpisodesDataSource @Inject constructor(
    private val rickAndMortyApi: RickAndMortyApi
) : EpisodesRemoteDataSource {
    override suspend fun fetchEpisodes(queries: HashMap<String, Int>): EpisodePaging {
        val data = rickAndMortyApi.getEpisode(queries)
        val episodes = data.results.map {
            it.toEpisodeModel()
        }
        return EpisodePaging(
            data.info.count,
            data.info.pages,
            data.info.next,
            data.info.prev,
            episodes
        )
    }

    override suspend fun fetchEpisodeById(episodeId: Int): Episode {
        return rickAndMortyApi.getEpisodeById(episodeId).toEpisodeModel()

    }

    override suspend fun fetchEpisodes(episodesIds: List<Int>): List<Episode> {
        return rickAndMortyApi.getEpisodesByIds(episodesIds).data.results.map {
            it.toEpisodeModel()
        }
    }
}