package com.eck.compose_migration.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AdvertDao {

    @Query("SELECT * FROM DbAdvert")
    suspend fun getAllAds(): List<DbAdvert>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAds(ads: List<DbAdvert>)
}