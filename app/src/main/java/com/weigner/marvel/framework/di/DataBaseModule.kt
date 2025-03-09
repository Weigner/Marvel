package com.weigner.marvel.framework.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.weigner.core.data.DbConstants.APP_DATABASE_NAME
import com.weigner.marvel.db.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    fun provideAppDataBase(@ApplicationContext context: Context): AppDataBase {
        return Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            APP_DATABASE_NAME
        ).build()
    }

}