package com.weigner.marvel.framework.di

import com.weigner.core.data.repository.EpisodesRemoteDataSource
import com.weigner.core.data.repository.EpisodesRepository
import com.weigner.marvel.framework.remote.RetrofitEpisodesDataSource
import com.weigner.marvel.framework.repository.EpisodesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindEpisodeRepository(repository: EpisodesRepositoryImpl): EpisodesRepository

    @Binds
    fun bindRemoteDataSource(dataSource: RetrofitEpisodesDataSource): EpisodesRemoteDataSource
}