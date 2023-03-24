package com.eck.compose_migration.network.models

import com.eck.compose_migration.db.DbAdvert
import com.google.gson.annotations.SerializedName

data class ApiAdvert(
    val id: Int,
    val make: String,
    val model: String,
    val price: Int,
    val firstRegistration: String?,
    val mileage: Int,
    val fuel: String,
    val images: List<ApiImageUrl>?,
    val description: String,
    val colour: String?,
    @SerializedName("modelline")
    val modelLine: String?,
    val seller: ApiAdvertSeller?
) {

    fun toDbAdvert(): DbAdvert {
        return DbAdvert(
            id = id,
            make = make,
            model = model,
            price = price,
            firstRegistration = firstRegistration,
            mileage = mileage,
            fuel = fuel,
            images = images?.map { it.url } ?: emptyList(),
            description = description,
            colour = colour,
            modelLine = modelLine,
            sellerType = seller?.type,
            phone = seller?.phone,
            city = seller?.city
        )
    }
}