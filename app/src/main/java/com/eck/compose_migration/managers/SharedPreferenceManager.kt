package com.eck.compose_migration.managers

import com.eck.compose_migration.ui.models.*

interface SharedPreferenceManager {

    var selectedSort: SortType
    var selectedFilterFuel: FilterFuelType
    var selectedFilterBrand: FilterBrandType
    var selectedFilterMileage: FilterMileageType
    var selectedFilterPrice: FilterPriceType
    var selectedFilterSeller: FilterSellerType

    fun toggleSavedAd(value: Int)
    fun isSavedAd(value: Int): Boolean
    fun getSavedAds(): List<Int>
}