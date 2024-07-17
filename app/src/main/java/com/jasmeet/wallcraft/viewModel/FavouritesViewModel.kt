package com.jasmeet.wallcraft.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jasmeet.wallcraft.model.apiResponse.local.FavouritesEntity
import com.jasmeet.wallcraft.model.repo.FavouritesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val repository: FavouritesRepo
) : ViewModel() {

    private val _favouritePhotos = MutableLiveData<List<FavouritesEntity>>()
    val favouritePhotos: LiveData<List<FavouritesEntity>> get() = _favouritePhotos

    init {
        getAllPhotos()
    }

    fun getAllPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            _favouritePhotos.postValue(repository.getAllPhotos())
        }
    }

    fun insertPhoto(photo: FavouritesEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertPhoto(photo)
            getAllPhotos() // Refresh the list after insertion
        }
    }

    fun deletePhoto(photo: FavouritesEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletePhoto(photo)
            getAllPhotos() // Refresh the list after deletion
        }
    }

    suspend fun isPhotoFavourite(id: String): Boolean {
        return repository.isPhotoFavourite(id)
    }
}
