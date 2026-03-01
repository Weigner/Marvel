package com.weigner.core.domain.model

data class EpisodePaging(
    val total: Int,
    val totalPages: Int,
    val nextPage: String,
    val previousPage: String?,
    val episodes: List<Episode>
)
