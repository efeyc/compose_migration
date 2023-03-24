package com.eck.compose_migration.network

import com.eck.compose_migration.network.models.ApiAdvert
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(
    private val api: Api
) : ApiService {

    override suspend fun getAds(): List<ApiAdvert> {
        return api.getAds()
    }
}




