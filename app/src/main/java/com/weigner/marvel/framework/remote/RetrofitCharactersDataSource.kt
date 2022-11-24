package com.weigner.marvel.framework.remote

import com.weigner.core.data.repository.CharactersRemoteDataSource
import com.weigner.marvel.framework.network.MarvelApi
import com.weigner.marvel.framework.network.response.DataWrapperResponse
import javax.inject.Inject

class RetrofitCharactersDataSource @Inject constructor(
    private val marvelApi: MarvelApi
) : CharactersRemoteDataSource<DataWrapperResponse> {
    override suspend fun fetchCharacters(queries: Map<String, String>): DataWrapperResponse {
        return marvelApi.getCharacters(queries)
    }
}