package com.weigner.marvel.framework

import com.weigner.core.data.repository.FavoriteLocalDataSource
import com.weigner.core.data.repository.FavoritesRepository
import com.weigner.core.domain.model.Character
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteLocalDataSource: FavoriteLocalDataSource
) : FavoritesRepository {
    override suspend fun getAll(): Flow<List<Character>> {
        return favoriteLocalDataSource.getAll()
    }

    override suspend fun saveFavorite(character: Character) {
        favoriteLocalDataSource.save(character)
    }

    override suspend fun deleteFavorite(character: Character) {
        favoriteLocalDataSource.delete(character)
    }
}