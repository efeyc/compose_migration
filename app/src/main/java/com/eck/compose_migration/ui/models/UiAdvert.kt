package com.eck.compose_migration.ui.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UiAdvert(
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
) : Parcelable

