package com.eck.compose_migration.ui.models

import android.content.Context
import com.eck.compose_migration.R

enum class FilterMileageType(val value: Int) {
    NoFilter(-1),
    Zero(1),
    Until5000(5000),
    Until50000(50000);

    fun getTitle(context: Context): String {
        val resId = when (this) {
            Zero -> R.string.filter_mileage_zero
            Until5000 -> R.string.filter_mileage_until5000
            Until50000 -> R.string.filter_mileage_until50000
            else -> R.string.no_selection
        }
        return context.getString(resId)
    }
}

