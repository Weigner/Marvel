package com.weigner.core.data.repository

import androidx.paging.PagingSource
import com.weigner.core.domain.model.Character
import com.weigner.core.domain.model.Comic
import com.weigner.core.domain.model.Event

interface CharactersRepository {

    fun getCharacters(query: String): PagingSource<Int, Character>

    suspend fun getComics(characterId: Int): List<Comic>

    suspend fun getEvents(characterId: Int): List<Event>
}