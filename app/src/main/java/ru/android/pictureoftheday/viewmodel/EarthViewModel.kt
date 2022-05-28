package ru.android.pictureoftheday.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.android.pictureoftheday.BuildConfig
import ru.android.pictureoftheday.api.PictureOfEarthResponse
import ru.android.pictureoftheday.domain.EarthRepository
import ru.android.pictureoftheday.util.BASE_URL_EARTH
import ru.android.pictureoftheday.util.NETWORK_ERROR
import ru.android.pictureoftheday.util.TAG
import ru.android.pictureoftheday.util.URL_EARTH_IMAGE
import java.io.IOException

class EarthViewModel(private val repository: EarthRepository) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: Flow<Boolean> = _loading

    private val _error: MutableSharedFlow<String> = MutableSharedFlow()
    val error: Flow<String> = _error

    private val _dataResponseSourceOfEarth: MutableStateFlow<PictureOfEarthResponse?> =
        MutableStateFlow(null)
    val dataResponseSourceOfEarth: MutableStateFlow<PictureOfEarthResponse?> =
        _dataResponseSourceOfEarth

    fun requestPictureOfEarth(date: String) {

        _loading.value = true

        viewModelScope.launch {
            try {
                val listOfCondition: PictureOfEarthResponse = repository.pictureOfEarth(date)

                for (arrayCondition in listOfCondition) {
                    arrayCondition.urlImage = constructUrlImageEarth(date, arrayCondition.imageName)
                }
//                Log.d(TAG, "requestPictureOfEarth() called: $listOfCondition")

                _dataResponseSourceOfEarth.emit(listOfCondition)

            } catch (e: IOException) {
                _error.emit(NETWORK_ERROR)
            }
            _loading.emit(false)
        }
    }
}

private fun constructUrlImageEarth(date: String, imageEarth: String): String {
    val yearDate = date.substring(0, 4)
    val monthDate = date.substring(5, 7)
    val dayDate = date.substring(8, 10)
    val searchUrl = "$yearDate/$monthDate/$dayDate/png/$imageEarth.png?api_key="
    return StringBuilder()
        .append(BASE_URL_EARTH, URL_EARTH_IMAGE, searchUrl, BuildConfig.NASA_API_KEY)
        .toString()
}

class EarthViewModelFactory(private val repository: EarthRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = EarthViewModel(repository) as T
}