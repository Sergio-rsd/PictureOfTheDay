package ru.android.pictureoftheday.domain.earth

import ru.android.pictureoftheday.api.earth.PictureOfEarthResponse

interface EarthRepository {
    suspend fun pictureOfEarth(date: String): PictureOfEarthResponse
/*
    suspend fun pictureOfEarthOnDate(
        year: String,
        month: String,
        day: String,
        imageName: String
    ): PictureOfEarthOnDateResponse
    */
}