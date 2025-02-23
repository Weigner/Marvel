package com.weigner.marvel.framework

import androidx.paging.PagingSource
import com.weigner.core.data.repository.CharactersRemoteDataSource
import com.weigner.core.data.repository.CharactersRepository
import com.weigner.core.domain.model.Character
import com.weigner.core.domain.model.Comic
import com.weigner.core.domain.model.Event
import com.weigner.marvel.framework.paging.CharactersPagingSource
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val remoteDataSource: CharactersRemoteDataSource
) : CharactersRepository{

    override fun getCharacters(query: String): PagingSource<Int, Character> {
        return CharactersPagingSource(remoteDataSource, query)
    }

    override suspend fun getComics(characterId: Int): List<Comic> {
        return remoteDataSource.fetchComics(characterId)
    }

    override suspend fun getEvents(characterId: Int): List<Event> {
        return remoteDataSource.fetchEvents(characterId)
    }
}