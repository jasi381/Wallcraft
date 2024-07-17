package com.jasmeet.wallcraft.model.apiResponse.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites")
data class FavouritesEntity(
    @PrimaryKey
    val id: String,
    val photoUrl: String,
    val lowQualityUrl: String,
    val photoData: ByteArray // New field for storing photo data as ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FavouritesEntity) return false

        if (id != other.id) return false
        if (photoUrl != other.photoUrl) return false
        if (!photoData.contentEquals(other.photoData)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + photoUrl.hashCode()
        result = 31 * result + photoData.contentHashCode()
        return result
    }
}
