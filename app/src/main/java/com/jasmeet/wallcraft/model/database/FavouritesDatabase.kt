package com.jasmeet.wallcraft.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jasmeet.wallcraft.model.apiResponse.local.FavouritesEntity
import com.jasmeet.wallcraft.model.dao.FavouriteDao

@Database(entities = [FavouritesEntity::class], version = 1, exportSchema = false)
abstract class FavouritesDatabase : RoomDatabase() {
    abstract fun favouriteDao(): FavouriteDao
}