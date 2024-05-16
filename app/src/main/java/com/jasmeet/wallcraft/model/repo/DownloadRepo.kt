package com.jasmeet.wallcraft.model.repo

interface DownloadRepo {
    suspend fun downloadFile(url: String)
}
