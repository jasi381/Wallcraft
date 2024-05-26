package com.jasmeet.wallcraft.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.firebase.auth.AuthResult
import com.jasmeet.wallcraft.MainDispatcherRule
import com.jasmeet.wallcraft.model.repo.FirebaseRepo
import com.jasmeet.wallcraft.model.userInfo.UserInfo
import com.jasmeet.wallcraft.viewModel.LoginSignUpViewModel
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
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
class LoginSignUpViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: LoginSignUpViewModel
    private lateinit var repo: FirebaseRepo

    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        repo = mock()
        viewModel = LoginSignUpViewModel(repo)
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `signUpWithEmailPassword success`() = runTest {
        // Given
        val email = "test@gmail.com"
        val password = "password"
        val authResult = mock<AuthResult>()
        whenever(repo.signUpWithEmailAndPassword(any(), any())).thenReturn(authResult)

        // When
        viewModel.signUpEmailPassword(email, password) {}

        // Then
        verify(repo).signUpWithEmailAndPassword(email, password)
        verify(repo).saveUserInfo(authResult)
        assertEquals(null, viewModel.errorState.value)
    }

    @Test
    fun `signUpEmailPassword failure`() = runTest {
        // Given
        val invalidEmail = "invalid-email"
        val password = "password"
        val errorMessage = "Invalid email format"
        whenever(repo.signUpWithEmailAndPassword(any(), any())).thenThrow(
            RuntimeException(
                errorMessage
            )
        )

        // When
        viewModel.signUpEmailPassword(invalidEmail, password) {}

        // Then
        verify(repo).signUpWithEmailAndPassword(invalidEmail, password)
        assertEquals(errorMessage, viewModel.errorState.value)
    }

    @Test
    fun `loginWithEmailPassword success`() = runTest {
        // Given
        val email = "test@gmail.com"
        val password = "password"
        val authResult = mock<AuthResult>()
        whenever(repo.loginWithEmailAndPassword(any(), any())).thenReturn(authResult)

        // When
        viewModel.loginWithEmailPassword(email, password) {}

        // Then
        verify(repo).loginWithEmailAndPassword(email, password)

        assertEquals(null, viewModel.errorState.value)


    }

    @Test
    fun `loginWithEmailPassword failure`() = runTest {

        // Given
        val invalidEmail = "invalid-email"
        val password = "password"
        val errorMessage = "Invalid email format"

        whenever(repo.loginWithEmailAndPassword(any(), any())).thenThrow(
            RuntimeException(
                errorMessage
            )
        )

        // When
        viewModel.loginWithEmailPassword(invalidEmail, password) {}

        // Then
        verify(repo).loginWithEmailAndPassword(invalidEmail, password)
        assertEquals(errorMessage, viewModel.errorState.value)

    }


    @Test
    fun `resetPassword success`() = runTest {
        // Given
        val email = "test@gmail.com"
        val onResetPassword = mock<() -> Unit>()
        whenever(repo.sendPasswordResetEmail(any())).thenReturn(Unit)

        // When
        viewModel.resetPassword(email, onResetPassword)

        // Then
        verify(repo).sendPasswordResetEmail(email)
        verify(onResetPassword).invoke()
        assertEquals(false, viewModel.isLoading.value)
        assertNull(viewModel.errorState.value)
    }

    @Test
    fun `resetPassword failure`() = runTest {
        // Given
        val email = "invalid-email"
        val errorMessage = "Error sending password reset email"
        val onResetPassword = mock<() -> Unit>()
        whenever(repo.sendPasswordResetEmail(any())).thenThrow(RuntimeException(errorMessage))

        // When
        viewModel.resetPassword(email, onResetPassword)

        // Then
        verify(repo).sendPasswordResetEmail(email)
        verify(onResetPassword, never()).invoke()
        assertEquals(false, viewModel.isLoading.value)
        assertEquals(errorMessage, viewModel.errorState.value)
    }

    @Test
    fun `saveData success`() = runTest {
        // Given
        val authResult = mock<AuthResult>()
        whenever(repo.saveUserInfo(any())).thenReturn(Unit)

        // When
        viewModel.saveData(authResult)

        // Then
        verify(repo).saveUserInfo(authResult)
        assertEquals(false, viewModel.isLoading.value)
        assertNull(viewModel.errorState.value)
    }

    @Test
    fun `saveData failure`() = runTest {
        // Given
        val authResult = mock<AuthResult>()
        val errorMessage = "Error saving user info"
        whenever(repo.saveUserInfo(any())).thenThrow(RuntimeException(errorMessage))

        // When
        viewModel.saveData(authResult)

        // Then
        verify(repo).saveUserInfo(authResult)
        assertEquals(false, viewModel.isLoading.value)
        assertEquals(errorMessage, viewModel.errorState.value)
    }

    @Test
    fun `getUserInfo success`() = runTest {
        // Given
        val userInfo = UserInfo("test@gmail.com") // Mocked user info object
        whenever(repo.fetchUserInfo()).thenReturn(userInfo)

        // When
        viewModel.getUserInfo()

        // Then
        verify(repo).fetchUserInfo()
        assertEquals(userInfo, viewModel.userInfo.value)
        assertNull(viewModel.errorState.value)
    }

    @Test
    fun `getUserInfo failure`() = runTest {
        // Given
        val errorMessage = "Error fetching user info"
        whenever(repo.fetchUserInfo()).thenThrow(RuntimeException(errorMessage))

        // When
        viewModel.getUserInfo()

        // Then
        verify(repo).fetchUserInfo()
        assertEquals(errorMessage, viewModel.errorState.value)
    }



}

