package com.weigner.marvel.framework.local

import com.weigner.core.data.repository.FavoriteLocalDataSource
import com.weigner.core.domain.model.Character
import com.weigner.marvel.db.dao.FavoriteDao
import com.weigner.marvel.db.entity.FavoriteEntity
import com.weigner.marvel.db.entity.toCharactersModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomFavoriteLocalDataSource @Inject constructor(
    private val favoriteDao: FavoriteDao
) : FavoriteLocalDataSource {
    override fun getAll(): Flow<List<Character>> {
        return favoriteDao.loadFavorites().map {
            it.toCharactersModel()
        }
    }

    override suspend fun save(character: Character) {
        favoriteDao.insertFavorite(character.toFavoriteEntity())
    }

    override suspend fun delete(character: Character) {
        favoriteDao.deleteFavorite(character.toFavoriteEntity())
    }

    private fun Character.toFavoriteEntity(): FavoriteEntity {
        return FavoriteEntity(id, name, imageUrl)
    }
}