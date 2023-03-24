package com.eck.compose_migration.di

import android.app.Application
import android.content.Context
import com.eck.compose_migration.managers.LogManager
import com.eck.compose_migration.managers.LogManagerImpl
import com.eck.compose_migration.managers.SharedPreferenceManager
import com.eck.compose_migration.managers.SharedPreferenceManagerImpl
import com.eck.compose_migration.network.Api
import com.eck.compose_migration.network.ApiService
import com.eck.compose_migration.network.ApiServiceImpl
import com.eck.compose_migration.objects.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    internal fun provideSharedPreferenceManager(
        gson: Gson,
        context: Context
    ): SharedPreferenceManager {
        return SharedPreferenceManagerImpl(
            gson,
            context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
        )
    }

    @Provides
    @Singleton
    internal fun provideLogManager(): LogManager = LogManagerImpl()

    @Provides
    @Singleton
    internal fun provideCoroutineContext(): CoroutineContext = Dispatchers.Default

    @Provides
    @Singleton
    internal fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    internal fun api(): Api {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.API_URL)
            .client(client)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().serializeNulls().create()
                )
            )
            .build()

        return retrofit.create(Api::class.java)
    }

    @Provides
    @Singleton
    internal fun provideApiService(apiServiceImpl: ApiServiceImpl): ApiService = apiServiceImpl
}