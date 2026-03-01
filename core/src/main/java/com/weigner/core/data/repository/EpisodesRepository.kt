package com.weigner.core.data.repository

import androidx.paging.PagingSource
import com.weigner.core.domain.model.Episode

interface EpisodesRepository {

    fun getEpisodes(): PagingSource<Int, Episode>

    suspend fun getEpisode(episodeId: Int): Episode

    suspend fun getEpisodes(episodesIds: List<Int>): List<Episode>
}