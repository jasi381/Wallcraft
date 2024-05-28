package com.jasmeet.wallcraft.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jasmeet.wallcraft.MainDispatcherRule
import com.jasmeet.wallcraft.model.apiResponse.remote.categoriesApiResponse.CategoriesApiResponse
import com.jasmeet.wallcraft.model.repo.CategoriesRepo
import com.jasmeet.wallcraft.viewModel.CategoriesViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class CategoriesViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val dispatcher = UnconfinedTestDispatcher()

    private lateinit var repo: CategoriesRepo
    private lateinit var viewModel: CategoriesViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        repo = mockk(relaxed = true)
        viewModel = CategoriesViewModel(repo)

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getCategories should fetch data and update state`() = runTest {
        // Given
        val mockCategories = listOf(
            CategoriesApiResponse(
                coverPhoto = null,
                currentUserContributions = emptyList(),
                description = "Description 1",
                endsAt = "2023-01-01T00:00:00Z",
                featured = false,
                id = "id1",
                links = null,
                onlySubmissionsAfter = null,
                owners = emptyList(),
                previewPhotos = emptyList(),
                publishedAt = "2023-01-01T00:00:00Z",
                slug = "slug1",
                startsAt = "2023-01-01T00:00:00Z",
                status = "open",
                title = "Title 1",
                totalCurrentUserSubmissions = null,
                totalPhotos = 10,
                updatedAt = "2023-01-01T00:00:00Z",
                visibility = "public"
            ),
            CategoriesApiResponse(
                coverPhoto = null,
                currentUserContributions = emptyList(),
                description = "Description 2",
                endsAt = "2023-02-01T00:00:00Z",
                featured = false,
                id = "id2",
                links = null,
                onlySubmissionsAfter = null,
                owners = emptyList(),
                previewPhotos = emptyList(),
                publishedAt = "2023-02-01T00:00:00Z",
                slug = "slug2",
                startsAt = "2023-02-01T00:00:00Z",
                status = "open",
                title = "Title 2",
                totalCurrentUserSubmissions = null,
                totalPhotos = 20,
                updatedAt = "2023-02-01T00:00:00Z",
                visibility = "public"
            )
        )
        coEvery { repo.getCategories() } returns mockCategories

        // When
        viewModel.getCategories()

        // Then
        assertEquals(false, viewModel.isLoading.value)
        assertEquals(mockCategories, viewModel.categories.value)
        assertEquals("", viewModel.error.value)
        coVerify { repo.getCategories() }
    }

    @Test
    fun `getCategories should handle errors correctly`() = runTest {
        // Given
        val mockError = RuntimeException("Something went wrong")
        coEvery { repo.getCategories() } throws mockError

        // When
        viewModel.getCategories()

        // Then
        assertEquals(false, viewModel.isLoading.value)
        assertTrue(viewModel.categories.value.isEmpty())
        assertEquals("Something went wrong", viewModel.error.value)
        coVerify { repo.getCategories() }
    }


}