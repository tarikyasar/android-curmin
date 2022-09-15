package com.tarikyasar.curmin.di

import com.tarikyasar.curmin.common.Constants
import com.tarikyasar.curmin.data.remote.CurrencyApi
import com.tarikyasar.curmin.data.repository.currency.CurrencyRepositoryImpl
import com.tarikyasar.curmin.domain.repository.CurrencyRepository
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
    fun provideCurrencyApi(): CurrencyApi {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(Interceptor { chain ->
                val original = chain.request()

                val request = original.newBuilder()
                    .header("apiKey", Constants.API_KEY)
                    .method(original.method, original.body)
                    .build()

                chain.proceed(request)
            })
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCurrencyRepository(api: CurrencyApi): CurrencyRepository {
        return CurrencyRepositoryImpl(api)
    }
}