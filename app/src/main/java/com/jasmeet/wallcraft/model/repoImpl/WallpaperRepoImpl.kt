package com.jasmeet.wallcraft.model.repoImpl

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.jasmeet.wallcraft.model.WallpaperType
import com.jasmeet.wallcraft.model.repo.WallpaperRepo
import com.jasmeet.wallcraft.view.loadImageFromUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class WallpaperRepoImpl(private val context: Context) : WallpaperRepo {

    override suspend fun loadBitmapFromUrl(url: String): Bitmap? {
        val bitmap = withContext(Dispatchers.IO) {
            loadImageFromUrl(url)
        }
        return bitmap
    }

    override suspend fun setWallpaper(bitmap: Bitmap?, wallpaperType: WallpaperType) {
        if (bitmap != null) {
            val wallpaperManager = WallpaperManager.getInstance(context)

            try {
                when (wallpaperType) {
                    WallpaperType.HOME_SCREEN -> wallpaperManager.setBitmap(
                        bitmap,
                        null,
                        true,
                        WallpaperManager.FLAG_SYSTEM
                    )

                    WallpaperType.LOCK_SCREEN -> wallpaperManager.setBitmap(
                        bitmap,
                        null,
                        true,
                        WallpaperManager.FLAG_LOCK
                    )

                    WallpaperType.BOTH -> {
                        wallpaperManager.setBitmap(
                            bitmap,
                            null,
                            true, WallpaperManager.FLAG_LOCK
                        )
                    }

                    else -> {}

                }

            } catch (e: IOException) {
                e.printStackTrace()
                Log.e("SetWallpaper", "Error setting wallpaper: ${e.message}")

            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("SetWallpaper", "Error setting wallpaper: ${e.message}")
            }
        }
    }
}