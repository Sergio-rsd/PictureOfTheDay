package ru.android.pictureoftheday.domain

import ru.android.pictureoftheday.api.PictureOfEarthOnDateResponse
import ru.android.pictureoftheday.api.PictureOfEarthResponse

interface EarthRepository {
    suspend fun pictureOfEarth(date: String): PictureOfEarthResponse
    suspend fun pictureOfEarthOnDate(year:String, month:String, day: String, imageName: String) : PictureOfEarthOnDateResponse
}