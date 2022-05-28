package ru.android.pictureoftheday.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.annotations.SerializedName

@JsonIgnoreProperties(ignoreUnknown = true)
class PictureOfEarthResponse : ArrayList<PictureOfEarthResponse.PictureOfEarthOnDateResponse>() {
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class PictureOfEarthOnDateResponse(
        val identifier: String,
        val caption: String,
        @SerializedName("image")
        val imageName: String,
        val date: String,
        var urlImage: String
    )
}