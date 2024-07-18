package com.jasmeet.wallcraft.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jasmeet.wallcraft.model.apiResponse.local.DownloadsEntity
import com.jasmeet.wallcraft.model.dao.DownloadsDao

@Database(entities = [DownloadsEntity::class], version = 1, exportSchema = false)
abstract class DownloadsDatabase : RoomDatabase() {
    abstract fun downloadsDao(): DownloadsDao
}