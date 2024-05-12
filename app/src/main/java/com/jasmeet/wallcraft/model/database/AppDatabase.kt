package com.jasmeet.wallcraft.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jasmeet.wallcraft.model.apiResponse.local.PhotoEntity
import com.jasmeet.wallcraft.model.dao.PhotosDao

@Database(entities = [PhotoEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotosDao
}