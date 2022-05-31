package ru.android.pictureoftheday.viewmodel.earth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.android.pictureoftheday.BuildConfig
import ru.android.pictureoftheday.api.earth.PictureOfEarthResponse
import ru.android.pictureoftheday.domain.earth.EarthRepositoryImpl
import java.io.IOException

private const val BASE_URL_EARTH = "https://epic.gsfc.nasa.gov/"
private const val URL_EARTH_IMAGE = "archive/natural/"
private const val NETWORK_ERROR = "Network error"

class EarthViewModel() : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: Flow<Boolean> = _loading

    private val _error: MutableSharedFlow<String> = MutableSharedFlow()
    val error: Flow<String> = _error

    private val _dataResponseSourceOfEarth: MutableStateFlow<PictureOfEarthResponse?> =
        MutableStateFlow(null)
    val dataResponseSourceOfEarth: MutableStateFlow<PictureOfEarthResponse?> =
        _dataResponseSourceOfEarth

    private val repository: EarthRepositoryImpl = EarthRepositoryImpl()

    fun requestPictureOfEarth(date: String) {

        _loading.value = true

        viewModelScope.launch {
            try {
                val listOfCondition: PictureOfEarthResponse = repository.pictureOfEarth(date)
                listOfCondition.forEach {
                    it.urlImage = constructUrlImageEarth(date, it.imageName)
                }

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