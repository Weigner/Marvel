package com.weigner.core.data.repository

import com.weigner.core.domain.model.Episode
import com.weigner.core.domain.model.EpisodePaging

interface EpisodesRemoteDataSource {

    suspend fun fetchEpisodes(queries: HashMap<String, Int>): EpisodePaging

    suspend fun fetchEpisodeById(episodeId: Int): Episode

    suspend fun fetchEpisodes(episodesIds: List<Int>): List<Episode>
}