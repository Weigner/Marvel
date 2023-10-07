package com.weigner.core.domain.model

data class CharactersPaging(
    val offset: Int,
    val total: Int,
    val characters: List<Character>
)
