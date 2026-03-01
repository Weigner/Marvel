package com.weigner.marvel.framework.network

import com.weigner.marvel.framework.network.response.DataContainerResponse
import com.weigner.marvel.framework.network.response.DataWrapperResponse
import com.weigner.marvel.framework.network.response.EpisodeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface RickAndMortyApi {

    @GET("api/episode")
    suspend fun getEpisode(
        @QueryMap
        queries: HashMap<String, Int>
    ): DataContainerResponse<EpisodeResponse>

    @GET("/episode/")
    suspend fun getEpisodeById(
        @Path("episodeId")
        episodeId: Int
    ): EpisodeResponse

    @GET("/episode")
    suspend fun getEpisodesByIds(
        @Path("episodeIds")
        episodeIds: List<Int>
    ): DataWrapperResponse<EpisodeResponse>
}