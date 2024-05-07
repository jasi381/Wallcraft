package com.jasmeet.wallcraft.view

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.jasmeet.wallcraft.model.WallpaperType
import com.jasmeet.wallcraft.view.navigation.WallCraftNavigator
import com.jasmeet.wallcraft.view.theme.WallcraftTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            )
        )
        setContent {
            WallcraftTheme {
                WallCraftNavigator(navController = rememberNavController())

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val url =
        "https://images.unsplash.com/photo-1636840438199-9125cd03c3b0?q=80&w=1035&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var isLoading by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Button(
            onClick = {
                isLoading = true
                scope.launch {
                    val bitmap = withContext(Dispatchers.IO) {
                        loadImageFromUrl(url)
                    }
                    setWallpaper(bitmap, context)
                    isLoading = false
                }
            }
        ) {
            Text(text = "Set Wallpaper")
        }

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

suspend fun loadImageFromUrl(url: String): Bitmap? = withContext(Dispatchers.IO) {
    try {
        val url1 = URL(url)
        val connection: HttpURLConnection = url1.openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()
        val input: InputStream = connection.inputStream
        BitmapFactory.decodeStream(input)
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

suspend fun setWallpaper(bitmap: Bitmap?, context: Context) = withContext(Dispatchers.IO) {
    if (bitmap != null) {
        val wallpaperManager = WallpaperManager.getInstance(context)
        try {
            wallpaperManager.setBitmap(bitmap)
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("SetWallpaper", "Error setting wallpaper: ${e.message}")
        }
    }
}


@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    val url =
        "https://images.unsplash.com/3/jerry-adney.jpg?q=80&w=1176&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var isLoading by remember { mutableStateOf(false) }
    var selectedWallpaperType by remember { mutableStateOf(WallpaperType.BOTH) }

    Column(modifier = modifier) {
        Button(
            onClick = {
                isLoading = true
                scope.launch {
                    val bitmap = withContext(Dispatchers.IO) {
                        loadImageFromUrl(url)
                    }
                    setWallpaper2(bitmap, context, selectedWallpaperType)
                    isLoading = false
                }
            }
        ) {
            Text(text = "Set Wallpaper")
        }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text("Set Wallpaper For:")
            Spacer(modifier = Modifier.width(8.dp))
            RadioButton(
                selected = selectedWallpaperType == WallpaperType.HOME_SCREEN,
                onClick = { selectedWallpaperType = WallpaperType.HOME_SCREEN },
                enabled = !isLoading
            )
            Text("Home Screen")
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(
                selected = selectedWallpaperType == WallpaperType.LOCK_SCREEN,
                onClick = { selectedWallpaperType = WallpaperType.LOCK_SCREEN },
                enabled = !isLoading
            )
            Text("Lock Screen")
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(
                selected = selectedWallpaperType == WallpaperType.BOTH,
                onClick = { selectedWallpaperType = WallpaperType.BOTH },
                enabled = !isLoading
            )
            Text("Both")
        }

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}


suspend fun setWallpaper2(bitmap: Bitmap?, context: Context, wallpaperType: WallpaperType) {
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
                    wallpaperManager.setBitmap(bitmap)
                    wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
                }
                else -> {}
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("SetWallpaper", "Error setting wallpaper: ${e.message}")
        }
    }
}
