package com.weigner.core.data.repository

import com.weigner.core.domain.model.CharactersPaging

interface CharactersRemoteDataSource {

    suspend fun fetchCharacters(queries: Map<String, String>): CharactersPaging
}