package com.jasmeet.wallcraft.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jasmeet.wallcraft.MainDispatcherRule
import com.jasmeet.wallcraft.model.apiResponse.remote.categoryDetailsApiResponse.CategoryDetailsApiResponse
import com.jasmeet.wallcraft.model.repo.CategoryDetailsRepo
import com.jasmeet.wallcraft.viewModel.CategoryDetailsViewModel
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`


@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class CategoryDetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val dispatcher = UnconfinedTestDispatcher()

    private lateinit var repo: CategoryDetailsRepo
    private lateinit var viewModel: CategoryDetailsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        repo = mock()
        viewModel = CategoryDetailsViewModel(repo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `getCategoryDetails success`() = runTest {
        val query = "wallpapers"
        val response = CategoryDetailsApiResponse(listOf(), 0, 0)
        `when`(repo.getCategoryDetails(query, 0)).thenReturn(response)

        viewModel.getCategoryDetails(query, 0)

        advanceUntilIdle()

        assert(!viewModel.loading.first())
        assert(viewModel.details.first() == response)
    }

    @Test
    fun `getCategoryDetails error`() = runTest {
        val query = "wallpapers"
        val page = 0
        val errorMessage = "Network Error"

        // Mock the repository to throw an exception
        `when`(repo.getCategoryDetails(query, page)).thenThrow(RuntimeException(errorMessage))

        // Call the method to be tested
        viewModel.getCategoryDetails(query, page)

        // Advance the coroutine to ensure all work is done
        advanceUntilIdle()

        // Assert the loading state is false after the operation
        assert(!viewModel.loading.first())

        // Assert the error state is as expected
        assert(viewModel.error.first() == "Failed to fetch details: $errorMessage")
    }


}