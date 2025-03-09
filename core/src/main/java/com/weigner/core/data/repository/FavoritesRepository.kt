package com.weigner.core.data.repository

import com.weigner.core.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {

    suspend fun getAll(): Flow<List<Character>>

    suspend fun saveFavorite(character: Character)

    suspend fun deleteFavorite(character: Character)
}