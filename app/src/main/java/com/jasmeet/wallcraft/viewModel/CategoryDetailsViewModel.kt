package com.jasmeet.wallcraft.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jasmeet.wallcraft.model.apiResponse.remote.categoryDetailsApiResponse.CategoryDetailsApiResponse
import com.jasmeet.wallcraft.model.repo.CategoryDetailsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryDetailsViewModel @Inject constructor(
    private val categoryDetailsRepo: CategoryDetailsRepo
) : ViewModel() {

    private val _details: MutableStateFlow<CategoryDetailsApiResponse?> = MutableStateFlow(null)
    val details = _details.asStateFlow()

    private val _error: MutableStateFlow<String?> = MutableStateFlow(null)
    val error = _error.asStateFlow()

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun getCategoryDetails(query: String, page: Int) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = categoryDetailsRepo.getCategoryDetails(query, page)
                _details.value = response
            } catch (e: Exception) {
                _error.value = "Failed to fetch details: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}