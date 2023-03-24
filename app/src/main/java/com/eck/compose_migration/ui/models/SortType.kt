package com.eck.compose_migration.ui.models

import android.content.Context
import com.eck.compose_migration.R

enum class SortType {
    NoSort,
    PriceAsc,
    PriceDesc,
    CityAsc,
    CityDesc;

    fun getTitle(context: Context): String {
        val resId = when (this) {
            PriceAsc -> R.string.price_asc
            PriceDesc -> R.string.price_desc
            CityAsc -> R.string.city_asc
            CityDesc -> R.string.city_desc
            else -> R.string.no_selection
        }
        return context.getString(resId)
    }
}

