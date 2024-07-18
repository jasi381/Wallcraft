package com.jasmeet.wallcraft.model.apiResponse.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "downloads")
data class DownloadsEntity(
    @PrimaryKey
    val id: String,
    val photoUrl: String,
    val lowResPhotoUrl: String,
    val time: Long,

    )

