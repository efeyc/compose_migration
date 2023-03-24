package com.eck.compose_migration.di

import android.app.Application
import androidx.room.Room
import com.eck.compose_migration.db.AdvertDao
import com.eck.compose_migration.db.AppDatabase
import com.eck.compose_migration.db.AppDatabase.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RoomModule {

    @Singleton
    @Provides
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room
            .databaseBuilder(application, AppDatabase::class.java, DATABASE_NAME)
            .build()
    }

    @Singleton
    @Provides
    fun provideAdvertDao(appDatabase: AppDatabase): AdvertDao {
        return appDatabase.advertDao()
    }
}