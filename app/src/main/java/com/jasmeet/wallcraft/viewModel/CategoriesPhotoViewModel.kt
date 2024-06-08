package com.jasmeet.wallcraft.viewModel

import androidx.lifecycle.ViewModel
import com.jasmeet.wallcraft.model.repo.CategoryPhotosRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CategoriesPhotoViewModel @Inject constructor(
    private val repo: CategoryPhotosRepo
) : ViewModel() {
    init {
        loadData()
    }

    private fun loadData()
}