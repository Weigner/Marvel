package com.weigner.marvel.framework.network.response

import com.google.gson.annotations.SerializedName
import com.weigner.core.domain.model.Comic

data class ComicResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("thumbnail")
    val thumbnail: ThumbnailResponse
)

fun ComicResponse.toComicModel(): Comic {
    return Comic(
        id = this.id,
        imageUrl = "${this.thumbnail.path}.${this.thumbnail.extension}".replace("http", "https")
    )
}