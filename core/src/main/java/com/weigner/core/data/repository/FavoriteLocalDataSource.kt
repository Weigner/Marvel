package com.weigner.core.data.repository

import com.weigner.core.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface FavoriteLocalDataSource {

    suspend fun getAll(): Flow<List<Character>>

    suspend fun save(character: Character)

    suspend fun delete(character: Character)
}