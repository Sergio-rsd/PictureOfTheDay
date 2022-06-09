package ru.android.pictureoftheday.domain.gallery

import ru.android.pictureoftheday.api.gallery.GalleryPictureOfTheDayResponse

interface GalleryNasaRepository {
    suspend fun galleryPictureOfTheDay(
        start_date: String,
        end_date: String
    ): GalleryPictureOfTheDayResponse
}