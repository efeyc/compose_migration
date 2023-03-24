package com.eck.compose_migration.ui.models

import android.content.Context
import com.eck.compose_migration.R

enum class FilterSellerType(val value: String) {
    NoFilter(""),
    Dealer("dealer"),
    Private("private");

    fun getTitle(context: Context): String {
        val resId = when (this) {
            Dealer -> R.string.filter_seller_dealer
            Private -> R.string.filter_seller_private
            else -> R.string.no_selection
        }
        return context.getString(resId)
    }
}

