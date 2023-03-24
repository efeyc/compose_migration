package com.eck.compose_migration.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [DbAdvert::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun advertDao(): AdvertDao

    companion object {
        const val DATABASE_NAME = "commig_db"
    }
}