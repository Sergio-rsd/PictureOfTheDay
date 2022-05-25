package ru.android.pictureoftheday.api

import com.google.gson.annotations.SerializedName
import java.util.*

data class PictureOfEarthOnDateResponse (
    @SerializedName("identifier")
    val identifier: String,
    @SerializedName("caption")
    val caption: String,
    @SerializedName("image")
    val imageName: String,
    @SerializedName("date")
    val date: Date
)
