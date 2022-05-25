package ru.android.pictureoftheday.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.android.pictureoftheday.api.PictureOfTheDayResponse
import ru.android.pictureoftheday.domain.NasaRepository
import ru.android.pictureoftheday.util.NETWORK_ERROR
import java.io.IOException

class MainViewModel(private val repository: NasaRepository) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: Flow<Boolean> = _loading

    private val _error: MutableSharedFlow<String> = MutableSharedFlow()
    val error: Flow<String> = _error

    private val _dataResponseSource: MutableStateFlow<PictureOfTheDayResponse?> =
        MutableStateFlow(null)
    val dataResponseSource: MutableStateFlow<PictureOfTheDayResponse?> = _dataResponseSource


    fun requestPictureOfTheDay(date: String) {

        _loading.value = true

        viewModelScope.launch {
            try {
                val dataPicture: PictureOfTheDayResponse = repository.pictureOfTheDay(date).apply {
                    copyright
                    date
                    explanation
                    title
                    url
                }
                _dataResponseSource.emit(dataPicture)
            } catch (e: IOException) {
                _error.emit(NETWORK_ERROR)
            }
            _loading.emit(false)
        }
    }
}


class MainViewModelFactory(private val repository: NasaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = MainViewModel(repository) as T
}