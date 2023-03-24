package com.eck.compose_migration.ui.models

import android.content.Context
import com.eck.compose_migration.R

enum class FilterFuelType(val value: String) {
    NoFilter(""),
    Gasoline("gasoline"),
    Diesel("diesel");

    fun getTitle(context: Context): String {
        val resId = when (this) {
            Gasoline -> R.string.filter_fuel_gasoline
            Diesel -> R.string.filter_fuel_diesel
            else -> R.string.no_selection
        }
        return context.getString(resId)
    }
}

