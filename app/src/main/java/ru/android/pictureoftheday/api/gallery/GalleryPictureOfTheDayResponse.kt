package ru.android.pictureoftheday.api.gallery

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class GalleryPictureOfTheDayResponse(
    val date: String,
    val title: String,
    val url: String
)