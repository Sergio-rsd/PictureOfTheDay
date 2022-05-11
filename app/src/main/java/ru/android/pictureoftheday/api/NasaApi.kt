package ru.android.pictureoftheday.api

import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {
    @GET("planetary/apod")
    suspend fun pictureOfTheDay(
        @Query("date") date: String,
        @Query("api_key") key: String
    ): PictureOfTheDayResponse
}