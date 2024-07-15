package com.jasmeet.wallcraft.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jasmeet.wallcraft.MainDispatcherRule
import com.jasmeet.wallcraft.model.apiResponse.remote.categoryDetailsApiResponse.CategoryDetailsApiResponse
import com.jasmeet.wallcraft.model.repo.CategoryDetailsRepo
import com.jasmeet.wallcraft.viewModel.SearchViewModel
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
class SearchViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val dispatcher = UnconfinedTestDispatcher()

    private lateinit var repo: CategoryDetailsRepo
    private lateinit var viewModel: SearchViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        repo = mock()
        viewModel = SearchViewModel(repo)
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

        viewModel.getSearchResults(query, 0)

        advanceUntilIdle()

        assert(!viewModel.loading.first())
        assert(viewModel.details.first() == response)
    }

    @Test
    fun `getCategoryDetails error`() = runTest {
        val query = "wallpapers"
        val page = 0
        val errorMessage = "Network Error"


        `when`(repo.getCategoryDetails(query, page)).thenThrow(RuntimeException(errorMessage))


        viewModel.getSearchResults(query, page)


        advanceUntilIdle()


        assert(!viewModel.loading.first())


        assert(viewModel.error.first() == "Failed to fetch details: $errorMessage")
    }


    @Test
    fun `clearSearchResults sets details to null`() = runTest {
        // Mock the repository to return a response
        val query = "wallpapers"
        val response = CategoryDetailsApiResponse(listOf(), 0, 0)
        `when`(repo.getCategoryDetails(query, 0)).thenReturn(response)

        // Call getSearchResults to set some initial state
        viewModel.getSearchResults(query, 0)
        advanceUntilIdle()

        // Verify initial state is not null
        val initialDetails = viewModel.details.first()
        println("Initial details: $initialDetails")
        assert(initialDetails != null)

        // Call the clearSearchResults method
        viewModel.clearSearchResults()

        // Assert that details state is null
        val clearedDetails = viewModel.details.first()
        println("Cleared details: $clearedDetails")
        assert(clearedDetails == null)
    }


}