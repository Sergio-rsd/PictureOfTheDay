package ru.android.pictureoftheday.domain

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.android.pictureoftheday.BuildConfig
import ru.android.pictureoftheday.api.EarthApi
import ru.android.pictureoftheday.api.PictureOfEarthOnDateResponse
import ru.android.pictureoftheday.api.PictureOfEarthResponse
import ru.android.pictureoftheday.util.BASE_URL

class EarthRepositoryImpl : EarthRepository {

    private val api = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(OkHttpClient.Builder().apply {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }.build())
        .build()
        .create(EarthApi::class.java)

    override suspend fun pictureOfEarth(date: String): PictureOfEarthResponse =
        api.pictureOfEarth(date, BuildConfig.NASA_API_KEY)

    override suspend fun pictureOfEarthOnDate(
        year: String,
        month: String,
        day: String,
        imageName: String
    ): PictureOfEarthOnDateResponse =
        api.pictureOfEarthOnDate(year, month, day, imageName, BuildConfig.NASA_API_KEY)
}