package com.weigner.core.data.repository

import com.weigner.core.domain.model.CharactersPaging
import com.weigner.core.domain.model.Comic
import com.weigner.core.domain.model.Event

interface CharactersRemoteDataSource {

    suspend fun fetchCharacters(queries: Map<String, String>): CharactersPaging

    suspend fun fetchComics(characterId: Int): List<Comic>

    suspend fun fetchEvents(characterId: Int): List<Event>
}