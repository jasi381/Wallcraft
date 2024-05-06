package com.jasmeet.wallcraft.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jasmeet.wallcraft.model.apiResponse.local.DownloadEntity
import com.jasmeet.wallcraft.model.dao.DownloadDao

@Database(entities = [DownloadEntity::class], version = 1)
abstract class DownloadsDatabase : RoomDatabase() {
    abstract fun downloadDao(): DownloadDao

    companion object {
        @Volatile
        private var INSTANCE: DownloadsDatabase? = null

        fun getInstance(context: Context): DownloadsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DownloadsDatabase::class.java,
                    "downloads_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
