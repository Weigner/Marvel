package com.weigner.marvel.framework.di

import com.weigner.core.usecase.GetComicsUseCase
import com.weigner.core.usecase.GetComicsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn

@Module
@InstallIn
interface UseCaseModule {

    @Binds
    fun bindGetComicsUseCase(useCase: GetComicsUseCaseImpl): GetComicsUseCase
}