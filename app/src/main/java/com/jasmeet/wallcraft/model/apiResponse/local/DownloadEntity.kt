package com.jasmeet.wallcraft.model.apiResponse.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "downloads")
data class DownloadEntity(
    @PrimaryKey
    val url: String,
    val imageBytes: ByteArray?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DownloadEntity) return false

        return url == other.url &&
                imageBytes?.contentEquals(other.imageBytes) ?: (other.imageBytes == null)
    }

    override fun hashCode(): Int {
        var result = url.hashCode()
        result = 31 * result + (imageBytes?.contentHashCode() ?: 0)
        return result
    }
}

