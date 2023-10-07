package com.weigner.marvel.framework.remote

import com.weigner.core.data.repository.CharactersRemoteDataSource
import com.weigner.core.domain.model.CharactersPaging
import com.weigner.marvel.framework.network.MarvelApi
import com.weigner.marvel.framework.network.response.DataWrapperResponse
import com.weigner.marvel.framework.network.response.toCharacterModel
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
}