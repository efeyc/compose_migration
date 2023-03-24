package com.eck.compose_migration.ui.models

import android.content.Context
import com.eck.compose_migration.R

enum class FilterBrandType(val value: String) {
    NoFilter(""),
    Audi("audi"),
    BMW("bmw"),
    Ford("ford"),
    Porsche("porsche");

    fun getTitle(context: Context): String {
        val resId = when (this) {
            Audi -> R.string.filter_make_audi
            BMW -> R.string.filter_make_bmw
            Ford -> R.string.filter_make_ford
            Porsche -> R.string.filter_make_porsche
            else -> R.string.no_selection
        }
        return context.getString(resId)
    }
}

