package ru.android.pictureoftheday.domain.earth

import ru.android.pictureoftheday.api.earth.PictureOfEarthResponse

interface EarthRepository {
    suspend fun pictureOfEarth(date: String): PictureOfEarthResponse
}