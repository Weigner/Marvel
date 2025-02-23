package com.weigner.marvel.framework.di

import com.weigner.marvel.framework.imageLoader.GlideImageLoader
import com.weigner.marvel.framework.imageLoader.ImageLoader
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
interface AppModule {

    @Binds
    fun bindImageLoader(imageLoader: GlideImageLoader): ImageLoader
}