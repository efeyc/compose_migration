package com.eck.compose_migration.network

import com.eck.compose_migration.network.models.ApiAdvert

interface ApiService {

    suspend fun getAds(): List<ApiAdvert>
}