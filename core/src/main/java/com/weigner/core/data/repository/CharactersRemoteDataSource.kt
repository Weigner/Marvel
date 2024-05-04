package com.weigner.core.data.repository

import com.weigner.core.domain.model.CharacterPaging
import com.weigner.core.domain.model.Comic
import com.weigner.core.domain.model.Event

interface CharactersRemoteDataSource {

    suspend fun fetchCharacters(queries: Map<String, String>): CharacterPaging

    suspend fun fetchComics(characterId: Int): List<Comic>

    suspend fun fetchEvents(characterId: Int): List<Event>
}