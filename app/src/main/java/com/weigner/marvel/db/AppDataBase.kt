package com.weigner.marvel.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.weigner.marvel.db.dao.FavoriteDao
import com.weigner.marvel.db.entity.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao
}