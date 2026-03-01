package com.weigner.marvel.framework.network.response

import com.google.gson.annotations.SerializedName

data class DataContainerResponse<T>(

    @SerializedName("info")
    val info: InfoResponse,

    @SerializedName("results")
    val results: List<T>
)

data class InfoResponse(
    @SerializedName("count")
    val count: Int,

    @SerializedName("pages")
    val pages: Int,

    @SerializedName("next")
    val next: String,

    @SerializedName("prev")
    val prev: String?,
)