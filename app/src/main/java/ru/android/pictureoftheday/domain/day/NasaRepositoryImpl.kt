package ru.android.pictureoftheday.domain.day

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.android.pictureoftheday.BuildConfig
import ru.android.pictureoftheday.api.day.NasaApi
import ru.android.pictureoftheday.api.day.PictureOfTheDayResponse

private const val BASE_URL = "https://api.nasa.gov/"

class NasaRepositoryImpl : NasaRepository {

    private val api = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(OkHttpClient.Builder().apply {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }.build())
        .build()
        .create(NasaApi::class.java)

    override suspend fun pictureOfTheDay(date: String): PictureOfTheDayResponse =
        api.pictureOfTheDay(
            date,
            BuildConfig.NASA_API_KEY
        )
}