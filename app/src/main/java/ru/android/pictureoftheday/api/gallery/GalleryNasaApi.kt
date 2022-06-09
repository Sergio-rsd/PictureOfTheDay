package ru.android.pictureoftheday.api.gallery

import retrofit2.http.GET
import retrofit2.http.Query

interface GalleryNasaApi {
    @GET("planetary/apod")
    suspend fun galleryPictureOfTheDay(
        @Query("start_date") start_date: String,
        @Query("end_date") end_date: String,
        @Query("api_key") key: String
    ): GalleryPictureOfTheDayResponse
}