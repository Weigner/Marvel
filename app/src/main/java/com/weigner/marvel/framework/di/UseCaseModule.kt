package com.weigner.marvel.framework.di

import com.weigner.core.usecase.GetCharacterCategoriesUseCase
import com.weigner.core.usecase.GetCharacterCategoriesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindGetComicsUseCase(
        useCase: GetCharacterCategoriesUseCaseImpl
    ): GetCharacterCategoriesUseCase
}