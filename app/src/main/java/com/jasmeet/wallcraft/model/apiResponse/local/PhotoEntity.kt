package com.jasmeet.wallcraft.model.apiResponse.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class PhotoEntity(
    @PrimaryKey val id: Int,
    val photoUrl: String
)