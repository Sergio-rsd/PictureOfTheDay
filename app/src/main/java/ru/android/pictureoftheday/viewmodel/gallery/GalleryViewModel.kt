package ru.android.pictureoftheday.viewmodel.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.android.pictureoftheday.api.gallery.GalleryPictureOfTheDayResponse
import ru.android.pictureoftheday.domain.gallery.GalleryNasaRepositoryImpl
import java.io.IOException

private const val NETWORK_ERROR = "Network error"

class GalleryViewModel : ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading: Flow<Boolean> = _loading

    private val _error: MutableSharedFlow<String> = MutableSharedFlow()
    val error: Flow<String> = _error

    private val _dataResponseSourceOfGallery: MutableStateFlow<GalleryPictureOfTheDayResponse?> =
        MutableStateFlow(null)
    val dataResponseSourceOfGallery: MutableStateFlow<GalleryPictureOfTheDayResponse?> =
        _dataResponseSourceOfGallery

    private val repository: GalleryNasaRepositoryImpl = GalleryNasaRepositoryImpl()

    fun requestGalleryPictureOfTheDay(start_date: String, end_date: String) {
        _loading.value = true

        viewModelScope.launch {
            try {
                val dataGalleryPicture: GalleryPictureOfTheDayResponse =
                    repository.galleryPictureOfTheDay(start_date, end_date).apply {
                        date
                        title
                        url
                    }
                _dataResponseSourceOfGallery.emit(dataGalleryPicture)

            } catch (e: IOException) {
                _error.emit(NETWORK_ERROR)
            }
            _loading.emit(false)
        }
    }
}