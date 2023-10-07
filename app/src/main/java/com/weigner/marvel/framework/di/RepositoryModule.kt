package com.weigner.marvel.framework.di

import com.weigner.core.data.repository.CharactersRemoteDataSource
import com.weigner.core.data.repository.CharactersRepository
import com.weigner.marvel.framework.CharactersRepositoryImpl
import com.weigner.marvel.framework.network.response.DataWrapperResponse
import com.weigner.marvel.framework.remote.RetrofitCharactersDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindCharacterRepository(repository: CharactersRepositoryImpl): CharactersRepository

    @Binds
    fun bindRemoteDataSource(dataSource: RetrofitCharactersDataSource): CharactersRemoteDataSource
}