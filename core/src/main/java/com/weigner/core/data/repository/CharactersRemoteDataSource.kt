package com.weigner.core.data.repository

import com.weigner.core.domain.model.CharactersPaging
import com.weigner.core.domain.model.Comic

interface CharactersRemoteDataSource {

    suspend fun fetchCharacters(queries: Map<String, String>): CharactersPaging

    suspend fun fetchComics(characterId: Int): List<Comic>
}