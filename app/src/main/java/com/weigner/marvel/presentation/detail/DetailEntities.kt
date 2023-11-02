package com.weigner.marvel.presentation.detail

import androidx.annotation.StringRes

data class DetailChildVE(
    val id: Int,
    val imageUrl: String
)

data class DetailParentVE(
    @StringRes
    val categoryStringResId :Int,
    val detailChildList: List<DetailChildVE> = listOf()
)
