package com.jasmeet.wallcraft.model.repo

import android.graphics.Bitmap
import com.jasmeet.wallcraft.model.WallpaperType

interface WallpaperRepo {
    suspend fun loadBitmapFromUrl(url: String): Bitmap?
    suspend fun setWallpaper(bitmap: Bitmap?, wallpaperType: WallpaperType)
}