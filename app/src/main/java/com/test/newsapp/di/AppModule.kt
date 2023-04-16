package com.test.newsapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.test.newsapp.BuildConfig
import com.test.newsapp.data.repository.NewsAppRepository
import com.test.newsapp.helper.Constants.API_KEY
import com.test.newsapp.helper.Constants.BASE_URL
import com.test.newsapp.helper.Constants.TIME_OUT
import com.test.newsapp.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesUrl() = BASE_URL


    @Provides
    @Singleton
    fun connectionTimeOut() = TIME_OUT

    @Provides
    @Singleton
    fun providesGsonBuilder(): Gson = GsonBuilder().setLenient().create()


    @Provides
    @Singleton
    fun providesOkHttpClient() = if (BuildConfig.DEBUG) {
        var loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        val requestInterceptor = Interceptor { chain ->
            val url = chain.request()
                .url()
                .newBuilder().addQueryParameter("apiKey", API_KEY)
                .build()
            val request = chain.request().newBuilder().url(url).build()

            return@Interceptor chain.proceed(request)
        }

        OkHttpClient.Builder().addInterceptor(requestInterceptor).addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient.Builder().build()
    }


    @Singleton
    @Provides
    fun providesRetrofit(baseUrl: String, gson: Gson, client: OkHttpClient): ApiService =
        Retrofit.Builder().baseUrl(baseUrl).client(client)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
            .create(ApiService::class.java)

    @Singleton
    @Provides
    fun providesRepository(apiService: ApiService): NewsAppRepository {
        return NewsAppRepository(
            apiService
        )
    }
}
