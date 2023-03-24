package com.eck.compose_migration.ui.models

import android.content.Context
import com.eck.compose_migration.R

enum class FilterPriceType(val value: Int) {
    NoFilter(-1),
    Until5000(5000),
    Until10000(10000),
    Until25000(25000);

    fun getTitle(context: Context): String {
        val resId = when (this) {
            Until5000 -> R.string.filter_price_until5000
            Until10000 -> R.string.filter_price_until10000
            Until25000 -> R.string.filter_price_until25000
            else -> R.string.no_selection
        }
        return context.getString(resId)
    }
}

