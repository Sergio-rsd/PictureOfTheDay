package ru.android.pictureoftheday.domain.day

import ru.android.pictureoftheday.api.day.PictureOfTheDayResponse

interface NasaRepository {
    suspend fun pictureOfTheDay(date: String): PictureOfTheDayResponse
}