package ru.android.pictureoftheday.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.util.*
import kotlin.collections.ArrayList

@JsonIgnoreProperties(ignoreUnknown = true)
data class PictureOfEarthResponse(
//    val listOfEarth: MutableList<PictureOfEarthOnDateResponse> = LinkedList<PictureOfEarthOnDateResponse>()
    val listOfEarth: ArrayList<PictureOfEarthOnDateResponse>
//    = LinkedList<PictureOfEarthOnDateResponse>()
//    val listOfEarth: List<String>
)