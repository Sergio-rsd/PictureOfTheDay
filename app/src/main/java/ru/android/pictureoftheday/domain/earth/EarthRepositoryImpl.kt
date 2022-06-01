package ru.android.pictureoftheday.domain.earth

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.android.pictureoftheday.BuildConfig
import ru.android.pictureoftheday.api.earth.EarthApi
import ru.android.pictureoftheday.api.earth.PictureOfEarthResponse

private const val BASE_URL_EARTH = "https://epic.gsfc.nasa.gov/"

class EarthRepositoryImpl : EarthRepository {
    private val api = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL_EARTH)
        .client(OkHttpClient.Builder().apply {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }.build())
        .build()
        .create(EarthApi::class.java)

    override suspend fun pictureOfEarth(date: String): PictureOfEarthResponse =
        api.pictureOfEarth(date, BuildConfig.NASA_API_KEY)

}
