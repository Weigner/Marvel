package com.weigner.marvel.framework.remote

import com.weigner.core.data.repository.CharactersRemoteDataSource
import com.weigner.core.domain.model.CharactersPaging
import com.weigner.core.domain.model.Comic
import com.weigner.marvel.framework.network.MarvelApi
import com.weigner.marvel.framework.network.response.toCharacterModel
import com.weigner.marvel.framework.network.response.toComicModel
import javax.inject.Inject

class RetrofitCharactersDataSource @Inject constructor(
    private val marvelApi: MarvelApi
) : CharactersRemoteDataSource {
    override suspend fun fetchCharacters(queries: Map<String, String>): CharactersPaging {
        val data = marvelApi.getCharacters(queries).data
        val characters = data.results.map {
            it.toCharacterModel()
        }
        return CharactersPaging(
            data.offset,
            data.total,
            characters
        )
    }

    override suspend fun fetchComics(characterId: Int): List<Comic> {
        return marvelApi.getComics(characterId).data.results.map {
            it.toComicModel()
        }
    }
}