package com.eck.compose_migration.helper

import com.eck.compose_migration.network.models.ApiAdvert
import com.eck.compose_migration.network.models.ApiAdvertSeller
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@Suppress("StringLiteralDuplication")
object TestConstants {

    val exception = RuntimeException("test")

    @OptIn(ExperimentalCoroutinesApi::class)
    val dispatcher = UnconfinedTestDispatcher()

    const val adId = 1

    val apiAdvert1 = ApiAdvert(
        id = 1, make = "make", model = "model", price = 1000, firstRegistration = "firstReg",
        mileage = 10000, fuel = "Diesel", images = emptyList(), description = "desc", colour = "white",
        modelLine = "modelline", seller = ApiAdvertSeller("Private", "Phone", "City")
    )
    val apiAdvert2 = ApiAdvert(
        id = 2, make = "make", model = "model", price = 1000, firstRegistration = "firstReg",
        mileage = 10000, fuel = "Diesel", images = emptyList(), description = "desc", colour = "white",
        modelLine = "modelline", seller = ApiAdvertSeller("Private", "Phone", "City")
    )
    val apiAdvert3 = ApiAdvert(
        id = 3, make = "make", model = "model", price = 1000, firstRegistration = "firstReg",
        mileage = 10000, fuel = "Diesel", images = emptyList(), description = "desc", colour = "white",
        modelLine = "modelline", seller = ApiAdvertSeller("Private", "Phone", "City")
    )

    val apiAds = listOf(apiAdvert1, apiAdvert2, apiAdvert3)
    val dbAds = apiAds.map { it.toDbAdvert() }
    val uiAds = dbAds.map { it.toUiAdvert() }

    const val searchKeyword = "engine"
    val searchedDbAds = apiAds.map { it.toDbAdvert() }.filter {
        it.description.contains(searchKeyword, ignoreCase = true) ||
                it.model.contains(searchKeyword, ignoreCase = true) ||
                it.make.contains(searchKeyword, ignoreCase = true) ||
                it.city?.contains(searchKeyword, ignoreCase = true) == true
    }
    val searchedUiAds = searchedDbAds.map { it.toUiAdvert() }
}