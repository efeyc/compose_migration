package com.eck.compose_migration.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eck.compose_migration.ui.models.UiAdvert

@Entity
data class DbAdvert(
    @PrimaryKey
    val id: Int = 0,
    val make: String,
    val model: String,
    val price: Int,
    val firstRegistration: String?,
    val mileage: Int,
    val fuel: String,
    val images: List<String>,
    val description: String,
    val colour: String?,
    val modelLine: String?,
    val sellerType: String?,
    val phone: String?,
    val city: String?
) {

    fun toUiAdvert(): UiAdvert {
        return UiAdvert(
            id = id,
            make = make,
            model = model,
            price = price,
            firstRegistration = firstRegistration,
            mileage = mileage,
            fuel = fuel,
            images = images,
            description = description,
            colour = colour,
            modelLine = modelLine,
            sellerType = sellerType,
            phone = phone,
            city = city
        )
    }

}