package com.weigner.marvel.framework.di

import com.weigner.core.data.repository.FavoriteLocalDataSource
import com.weigner.core.data.repository.FavoritesRepository
import com.weigner.marvel.framework.FavoriteRepositoryImpl
import com.weigner.marvel.framework.local.RoomFavoriteLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface FavoritesRepositoryModule {

    @Binds
    fun bindFavoritesRepository(repository: FavoriteRepositoryImpl): FavoritesRepository

    @Binds
    fun bindLocalDataSource(dataSource: RoomFavoriteLocalDataSource): FavoriteLocalDataSource
}