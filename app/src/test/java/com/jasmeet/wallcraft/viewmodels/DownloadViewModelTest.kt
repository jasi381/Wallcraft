package com.jasmeet.wallcraft.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jasmeet.wallcraft.MainDispatcherRule
import com.jasmeet.wallcraft.model.repo.DownloadRepo
import com.jasmeet.wallcraft.viewModel.DownloadViewModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException


@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class DownloadViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val dispatcher = UnconfinedTestDispatcher()


    private lateinit var viewModel: DownloadViewModel
    private lateinit var downloadRepo: DownloadRepo

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        downloadRepo = mockk()
        viewModel = DownloadViewModel(downloadRepo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `startDownload should call downloadRepo and update loading state`() = runBlockingTest {
        // Given
        val url = "https://i.imgur.com/OB0y6MR.jpg"
        val expectedMessage = "Download Complete"
        coEvery { downloadRepo.downloadFile(url) } just Runs

        // When
        viewModel.startDownload(url) { message ->
            // Then
            assertEquals(expectedMessage, message)
            assertFalse(viewModel.isLoading.value)
        }

        // Verify
        assertTrue(viewModel.isLoading.value)
        coVerify { downloadRepo.downloadFile(url) }
    }

    @Test
    fun `startDownload should update loading state on download failure`() = runBlockingTest {
        // Given
        val url = "https://i.imgur.com/OB0y6MR.jpg"
        coEvery { downloadRepo.downloadFile(url) } throws IOException()

        // When
        viewModel.startDownload(url) { message ->
            // Then
            assertEquals("Download Failed", message)
            assertFalse(viewModel.isLoading.value)
        }

        // Verify
        assertTrue(viewModel.isLoading.value)
        coVerify { downloadRepo.downloadFile(url) }
    }
}