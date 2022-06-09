package ru.android.pictureoftheday.domain.gallery

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.android.pictureoftheday.BuildConfig
import ru.android.pictureoftheday.api.gallery.GalleryNasaApi
import ru.android.pictureoftheday.api.gallery.GalleryPictureOfTheDayResponse

private const val BASE_URL = "https://api.nasa.gov/"

class GalleryNasaRepositoryImpl : GalleryNasaRepository {

    private val api = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(OkHttpClient.Builder().apply {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }.build())
        .build()
        .create(GalleryNasaApi::class.java)

    override suspend fun galleryPictureOfTheDay(
        start_date: String,
        end_date: String
    ): GalleryPictureOfTheDayResponse =
        api.galleryPictureOfTheDay(
            start_date,
            end_date,
            BuildConfig.NASA_API_KEY
        )
}