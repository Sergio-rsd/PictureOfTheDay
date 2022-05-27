package ru.android.pictureoftheday.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EarthApi {
    @GET("api/natural/date/{dateChoice}")
    suspend fun pictureOfEarth(
        @Path("dateChoice") date: String,
        @Query("api_key") key: String
    ): PictureOfEarthResponse

    @GET("EPIC/archive/natural/{yearChoice}/{monthChoice}/{dayChoice}/png/{image}.png")
    suspend fun pictureOfEarthOnDate(
        @Path("yearChoice") year:String,
        @Path("monthChoice") month:String,
        @Path("dayChoice") day: String,
        @Path("image") imageName: String,
        @Query("api_key") key: String
    ) : PictureOfEarthOnDateResponse
}