package com.jasmeet.wallcraft.model.apiResponse.local

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "downloads")
data class DownloadsEntity(
    @PrimaryKey
    val id: String,
    val photoUrl: String,
    val lowResPhotoUrl: String,
    val time: Long,

    )

