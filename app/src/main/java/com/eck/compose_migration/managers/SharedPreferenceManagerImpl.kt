package com.eck.compose_migration.managers

import android.content.SharedPreferences
import com.eck.compose_migration.ui.models.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferenceManagerImpl(
    private val gson: Gson,
    private val sharedPreferences: SharedPreferences,
) : SharedPreferenceManager {

    override var selectedSort: SortType
        get() = SortType.values()[getInt(KEY_SELECTED_SORT, 0)]
        set(value) {
            setInt(KEY_SELECTED_SORT, value.ordinal)
        }

    override var selectedFilterFuel: FilterFuelType
        get() = FilterFuelType.values()[getInt(KEY_SELECTED_FILTER_FUEL, 0)]
        set(value) {
            setInt(KEY_SELECTED_FILTER_FUEL, value.ordinal)
        }

    override var selectedFilterBrand: FilterBrandType
        get() = FilterBrandType.values()[getInt(KEY_SELECTED_FILTER_BRAND, 0)]
        set(value) {
            setInt(KEY_SELECTED_FILTER_BRAND, value.ordinal)
        }

    override var selectedFilterMileage: FilterMileageType
        get() = FilterMileageType.values()[getInt(KEY_SELECTED_FILTER_MILEAGE, 0)]
        set(value) {
            setInt(KEY_SELECTED_FILTER_MILEAGE, value.ordinal)
        }

    override var selectedFilterPrice: FilterPriceType
        get() = FilterPriceType.values()[getInt(KEY_SELECTED_FILTER_PRICE, 0)]
        set(value) {
            setInt(KEY_SELECTED_FILTER_PRICE, value.ordinal)
        }

    override var selectedFilterSeller: FilterSellerType
        get() = FilterSellerType.values()[getInt(KEY_SELECTED_FILTER_SELLER, 0)]
        set(value) {
            setInt(KEY_SELECTED_FILTER_SELLER, value.ordinal)
        }

    override fun toggleSavedAd(value: Int) {
        val savedItems = getSavedAds().toMutableList()
        if (!savedItems.contains(value)) {
            savedItems.add(value)
        } else {
            savedItems.remove(value)
        }
        setList(KEY_SAVED_ITEM_LIST, savedItems)
    }

    override fun getSavedAds(): List<Int> {
        return if (sharedPreferences.contains(KEY_SAVED_ITEM_LIST)) {
            val type = object : TypeToken<List<Int>>() {}.type
            gson.fromJson(getString(KEY_SAVED_ITEM_LIST), type)
        } else emptyList()
    }

    override fun isSavedAd(value: Int): Boolean {
        return getSavedAds().contains(value)
    }

    /**
     * Value Setters and getters
     */

    private fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    private fun setBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    private fun getFloat(key: String, defaultValue: Float): Float {
        return sharedPreferences.getFloat(key, defaultValue)
    }

    private fun setFloat(key: String, value: Float) {
        sharedPreferences.edit().putFloat(key, value).apply()
    }

    private fun getInt(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    private fun setInt(key: String, value: Int?) {
        value?.let {
            sharedPreferences.edit().putInt(key, it).apply()
        } ?: run {
            sharedPreferences.edit().remove(key).apply()
        }
    }

    private fun getLong(key: String): Long {
        return sharedPreferences.getLong(key, 0)
    }

    private fun setLong(key: String, value: Long) {
        sharedPreferences.edit().putLong(key, value).apply()
    }

    private fun getString(key: String): String {
        return getString(key, "")
    }

    private fun getString(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    private fun setString(key: String, value: String?) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    private fun <T> setList(key: String, list: List<T>) {
        val serializedObject = gson.toJson(list)
        setString(key, serializedObject)
    }

    companion object {
        private const val KEY_SAVED_ITEM_LIST = "key_saved_item_list"
        private const val KEY_SELECTED_SORT = "key_selected_sort"
        private const val KEY_SELECTED_FILTER_FUEL = "key_selected_filter_fuel"
        private const val KEY_SELECTED_FILTER_BRAND = "key_selected_filter_brand"
        private const val KEY_SELECTED_FILTER_MILEAGE = "key_selected_filter_mileage"
        private const val KEY_SELECTED_FILTER_PRICE = "key_selected_filter_price"
        private const val KEY_SELECTED_FILTER_SELLER = "key_selected_filter_seller"
    }
}