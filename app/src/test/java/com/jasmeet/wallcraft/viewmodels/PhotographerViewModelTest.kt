package com.jasmeet.wallcraft.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jasmeet.wallcraft.MainDispatcherRule
import com.jasmeet.wallcraft.model.apiResponse.remote.postedByApiResponse.PostedByApiResponse
import com.jasmeet.wallcraft.model.repo.PhotographerPhotosRepo
import com.jasmeet.wallcraft.model.repo.PostedByRepo
import com.jasmeet.wallcraft.viewModel.PhotographerDetailsViewModel
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`


@OptIn(ExperimentalCoroutinesApi::class)
class PhotographerViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: PhotographerDetailsViewModel
    private lateinit var photographerPhotosRepo: PhotographerPhotosRepo
    private lateinit var postedByRepo: PostedByRepo


    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        postedByRepo = mock()
        photographerPhotosRepo = mock()
        viewModel = PhotographerDetailsViewModel(postedByRepo, photographerPhotosRepo)
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getDetails should update details and isLoading state`() = runTest {
        val name = "John Doe"
        val response = PostedByApiResponse(listOf(), 0, 0)
        `when`(postedByRepo.getPhotographerDetails(name)).thenReturn(response)

        viewModel.getDetails(name)

        advanceUntilIdle()

        assert(!viewModel.isLoading.first())
        assert(viewModel.details.first() == response)
        verify(postedByRepo).getPhotographerDetails(name)
    }


    @Test
    fun `getDetails should handle error and update error state`() = runTest {
        val name = "John Doe"
        val errorMessage = "Error occurred"
        `when`(postedByRepo.getPhotographerDetails(name)).thenThrow(RuntimeException(errorMessage))

        viewModel.getDetails(name)

        advanceUntilIdle()

        Assert.assertFalse(viewModel.isLoading.first())
        Assert.assertEquals(errorMessage, viewModel.error.first())
        verify(postedByRepo).getPhotographerDetails(name)
    }


    @Test
    fun `getPhotos should handle error and update error state`() = runTest {
        val name = "John Doe"
        val orderBy = "latest"
        val errorMessage = "Error occurred"
        `when`(photographerPhotosRepo.getPhotos(name, orderBy)).thenThrow(
            RuntimeException(
                errorMessage
            )
        )

        viewModel.getPhotos(name, orderBy)

        advanceUntilIdle()

        Assert.assertFalse(viewModel.isLoading.first())
        Assert.assertEquals(errorMessage, viewModel.error.first())
        verify(photographerPhotosRepo).getPhotos(name, orderBy)
    }
}