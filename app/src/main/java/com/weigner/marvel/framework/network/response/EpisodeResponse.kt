package com.weigner.marvel.framework.network.response

import com.google.gson.annotations.SerializedName
import com.weigner.core.domain.model.Episode

data class EpisodeResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("thumbnail")
    val thumbnail: String? = "https://rickandmortyapi.com/api/character/avatar/38.jpeg"
)

fun EpisodeResponse.toEpisodeModel(): Episode {
    return Episode(
        id = this.id,
        imageUrl = this.thumbnail ?: "https://rickandmortyapi.com/api/character/avatar/38.jpeg"
    )
}