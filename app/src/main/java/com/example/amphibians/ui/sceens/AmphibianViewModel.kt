package com.example.amphibians.ui.sceens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.amphibians.AmphibianPhotosApplication
import com.example.amphibians.data.AmphibianPhotosRepository
import com.example.amphibians.model.AmphibianPhoto
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface AmphbianUIState {
    data class Success(val photos: List<AmphibianPhoto>) : AmphbianUIState
    object Error : AmphbianUIState
    object Loading : AmphbianUIState
}

class AmphibianViewModel(
    private val amphibianPhotosRepository: AmphibianPhotosRepository
) : ViewModel() {
    /** Mutable State stores most recent request status */
    var amphibianUiState: AmphbianUIState by mutableStateOf(AmphbianUIState.Loading)
        private set

    /**
     * Call getAmphibianPhotos() on init to display status immediately.
     */
    init {
        getAmphibianPhotos()
    }

    /**
     * Gets Amphibian photos information from the Amphibian API Retrofit service and updates the
     * [AmphibianPhoto] [List] [MutableList].
     */
    fun getAmphibianPhotos() {
        viewModelScope.launch {
            amphibianUiState = try {
                AmphbianUIState.Success(amphibianPhotosRepository.getAmphibianPhotos())
            } catch (e: IOException) {
                AmphbianUIState.Error
            } catch (e: HttpException) {
                AmphbianUIState.Error
            }
        }
    }

    /**
     * A companion object helps us by having a single instance of an object that is used by everyone
     * without needing to create a new instance of an expensive object. This is an implementation
     * detail, and separating it lets us make changes without impacting other parts of the app's code.
     *
     * The APPLICATION_KEY is part of the ViewModelProvider.AndroidViewModelFactory.Companion object
     * and is used to find the app's MarsPhotosApplication object, which has the container property
     * used to retrieve the repository used for dependency injection.
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AmphibianPhotosApplication)
                val amphibianPhotosRepository = application.container.amphibianPhotosRepository
                AmphibianViewModel(
                    amphibianPhotosRepository = amphibianPhotosRepository
                )
            }
        }
    }

}