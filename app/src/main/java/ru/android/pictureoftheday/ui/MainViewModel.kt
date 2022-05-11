package ru.android.pictureoftheday.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.android.pictureoftheday.api.PictureOfTheDayResponse
import ru.android.pictureoftheday.domain.NasaRepository
import ru.android.pictureoftheday.util.TAG
import java.io.IOException

class MainViewModel(private val repository: NasaRepository) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: Flow<Boolean> = _loading
/*

    private val _image: MutableStateFlow<String?> = MutableStateFlow(null)
    val image: Flow<String?> = _image

    private val _description: MutableStateFlow<String?> = MutableStateFlow(null)
    val description: Flow<String?> = _description
*/

    private val _error: MutableSharedFlow<String> = MutableSharedFlow()
    val error: Flow<String> = _error

   /*
    private val _dataResponse: MutableStateFlow<MutableList<String>?> = MutableStateFlow(null)
    val dataResponse: MutableStateFlow<MutableList<String>?> = _dataResponse
    */

    private val _dataResponseSource: MutableStateFlow<PictureOfTheDayResponse?> = MutableStateFlow(null)
    val dataResponseSource : MutableStateFlow<PictureOfTheDayResponse?> = _dataResponseSource


    fun requestPictureOfTheDay(date:String) {

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
//                Log.d(TAG, "requestPictureOfTheDay() called: $dataPicture")
            } catch (e: IOException) {
                // TODO ресурс вывести
                _error.emit("Network error")
            }
            _loading.emit(false)
        }
    }
}


class MainViewModelFactory(private val repository: NasaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = MainViewModel(repository) as T
}