package com.jasmeet.wallcraft.model.repoImpl

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import com.jasmeet.wallcraft.R
import com.jasmeet.wallcraft.model.repo.DownloadRepo
import java.io.File

class DownloadRepoImpl(private val context: Context) : DownloadRepo {
    override suspend fun downloadFile(url: String) {
        val appName = context.getString(R.string.app_name)

        val storageDir =
            File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                appName
            )
        if (!storageDir.exists()) {
            storageDir.mkdirs()
        }

        val fileName = "image.jpg" // Name of the file to be saved
        val file = File(storageDir, fileName)

        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle("Image Download") // Title of the Download Notification
            .setDescription("Downloading") // Description of the Download Notification
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED) // Visibility of the download Notification
            .setAllowedOverMetered(true) //  if download is allowed on Mobile network
            .setAllowedOverRoaming(true) //  if download is allowed on roaming network
            .setDestinationUri(Uri.fromFile(file)) // Saving downloaded file to the specified destination

        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
    }
}