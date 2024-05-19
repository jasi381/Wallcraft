package com.jasmeet.wallcraft.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jasmeet.wallcraft.model.apiResponse.remote.categoriesApiResponse.CategoriesApiResponse
import com.jasmeet.wallcraft.model.repo.CategoriesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val categoriesRepo: CategoriesRepo
) : ViewModel() {

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _categories: MutableStateFlow<List<CategoriesApiResponse?>> =
        MutableStateFlow(emptyList())
    val categories = _categories.asStateFlow()

    private val _error: MutableStateFlow<String> = MutableStateFlow("")
    val error = _error.asStateFlow()


    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = categoriesRepo.getCategories()
                _categories.value = response
                Log.d("CategoriesViewModel", "getCategories: $response")
                _isLoading.value = false
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = e.message.toString()
                Log.d("CategoriesViewModel", "getCategories: ${e.message}")
                _isLoading.value = false
            }

        }
    }
}