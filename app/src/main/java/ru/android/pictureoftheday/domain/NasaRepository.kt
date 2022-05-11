package ru.android.pictureoftheday.domain

import ru.android.pictureoftheday.api.PictureOfTheDayResponse

interface NasaRepository {
    suspend fun pictureOfTheDay(date: String): PictureOfTheDayResponse
}