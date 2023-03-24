package com.eck.compose_migration.network

import com.eck.compose_migration.network.models.ApiAdvert
import retrofit2.http.*

interface Api {

    @GET("/")
    suspend fun getAds(): List<ApiAdvert>
}