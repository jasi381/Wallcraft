package com.jasmeet.wallcraft.viewmodels

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import androidx.activity.result.ActivityResultLauncher
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.content.FileProvider
import com.jasmeet.wallcraft.model.apiResponse.remote.detailsApiResponse.DetailsApiResponse
import com.jasmeet.wallcraft.model.repo.DetailsRepo
import com.jasmeet.wallcraft.utils.Utils
import com.jasmeet.wallcraft.viewModel.DetailsViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.File
import java.io.FileOutputStream


@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = UnconfinedTestDispatcher()

    private lateinit var repo: DetailsRepo
    private lateinit var context: Context
    private lateinit var viewModel: DetailsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        repo = mockk()
        context = mockk()
        viewModel = DetailsViewModel(repo, context)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `getDetails should fetch data and update state`() = runTest {
        // Given
        val mockDetails = DetailsApiResponse(
            id = "id1",
            slug = "slug1",
            alternativeSlugs = null,
            createdAt = "2023-01-01T00:00:00Z",
            updatedAt = "2023-01-01T00:00:00Z",
            promotedAt = "2023-01-01T00:00:00Z",
            width = 1080,
            height = 1920,
            color = "#FFFFFF",
            blurHash = "LEHV6nWB2yk8pyo0adR*.7kCMdnj",
            descrciption = "Sample description",
            altDescription = "Alternative description",
            breadcrumbs = null,
            urls = null,
            links = null,
            likes = 100,
            likedByUser = false,
            currentUserCollections = arrayListOf("collection1"),
            sponsorship = null,
            topicSubmissions = null,
            assetType = "image",
            user = null,
            exif = null,
            location = null,
            meta = null,
            publicDomain = true,
            tags = arrayListOf(),
            tagsPreview = null,
            views = 1000,
            downloads = 500,
            topics = arrayListOf(),
            relatedCollections = null
        )
        coEvery { repo.getWallpaperDetails(any()) } returns mockDetails
        // When
        viewModel.getDetails("id1")

        // Then
        assertEquals(mockDetails, viewModel.details.value)
        assertNull(viewModel.error.value)
        coVerify { repo.getWallpaperDetails("id1") }

    }

    @Test
    fun `getDetails should handle errors correctly`() = runTest {
        // Given
        val mockError = RuntimeException("Something went wrong")
        coEvery { repo.getWallpaperDetails(any()) } throws mockError

        // When
        viewModel.getDetails("id1")

        // Then
        assertNull(viewModel.details.value)
        assertEquals("Failed to fetch details: Something went wrong", viewModel.error.value)
        coVerify { repo.getWallpaperDetails("id1") }
    }


    /**
     * TODO: Fix this test:
     *
     * Pending test for shareImage
     */
    @Test
    fun `shareImage should share image correctly`() = runTest {
        // Given
        val imageUrl = "https://cdn.pixabay.com/photo/2014/09/20/23/44/website-454460_1280.jpg"
        val mockBitmap: Bitmap = mockk()
        mockkObject(Utils)
        every { Utils.getBitmapFromUrl(imageUrl) } returns mockBitmap

        val mockImagesDir = mockk<File>()
        every { context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) } returns mockImagesDir

        val mockImageFile = mockk<File>()
        every { File(any<File>(), any()) } returns mockImageFile

        every { mockImageFile.path } returns "/data/local/tmp"
        val mockFileOutputStream = mockk<FileOutputStream>(relaxed = true)
        every { FileOutputStream(mockImageFile) } returns mockFileOutputStream
        every { mockBitmap.compress(any(), any(), any()) } returns true

        val mockUri = mockk<Uri>()
        every { FileProvider.getUriForFile(any(), any(), any()) } returns mockUri

        val mockShareImageLauncher: ActivityResultLauncher<Intent> = mockk(relaxed = true)

        // When
        viewModel.shareImage(imageUrl, mockShareImageLauncher)

        // Then
        coVerify { mockShareImageLauncher.launch(any()) }
    }

}
