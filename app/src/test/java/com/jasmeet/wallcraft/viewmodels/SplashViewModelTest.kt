package com.jasmeet.wallcraft.viewmodels

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.jasmeet.wallcraft.viewModel.SplashViewModel
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SplashViewModelTest {

    private lateinit var viewModel: SplashViewModel

    @Mock
    private lateinit var auth: FirebaseAuth

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = SplashViewModel(auth)
    }

    @Test
    fun `checkForActiveSession should update isUserLoggedIn`() {
        // Given
        val mockCurrentUser = mock(FirebaseUser::class.java)
        `when`(auth.currentUser).thenReturn(mockCurrentUser)

        // When
        viewModel.checkForActiveSession()

        // Then
        assertTrue(viewModel.isUserLoggedIn.value!!)
    }

    @Test
    fun `checkForActiveSession should update isUserLoggedIn to false`() {
        // Given
        `when`(auth.currentUser).thenReturn(null)

        // When
        viewModel.checkForActiveSession()

        // Then
        assertFalse(viewModel.isUserLoggedIn.value!!)
    }
}
