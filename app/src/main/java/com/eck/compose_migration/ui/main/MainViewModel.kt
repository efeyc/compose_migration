package com.eck.compose_migration.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eck.compose_migration.db.AdvertDao
import com.eck.compose_migration.managers.LogManager
import com.eck.compose_migration.managers.SharedPreferenceManager
import com.eck.compose_migration.network.ApiService
import com.eck.compose_migration.ui.base.BaseViewModel
import com.eck.compose_migration.ui.models.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiService: ApiService,
    private val logManager: LogManager,
    private val sharedPreferenceManager: SharedPreferenceManager,
    private val advertDao: AdvertDao,
    coroutineContext: CoroutineContext = Dispatchers.Default,
) : BaseViewModel(coroutineContext) {

    private val adsLiveData = MutableLiveData<List<UiAdvert>>()

    fun getData(): LiveData<List<UiAdvert>> {
        return adsLiveData
    }

    fun fetchData() {
        scope.launch {
            try {
                val dbAds = advertDao.getAllAds()
                sortFilterPostAds(dbAds.map { it.toUiAdvert() })

                val ads = apiService.getAds().map { it.toDbAdvert() }
                advertDao.insertAds(ads)
                sortFilterPostAds(dbAds.map { it.toUiAdvert() })
            } catch (exception: Exception) {
                logManager.logError(exception)
            }
        }
    }

    private fun sortFilterPostAds(ads: List<UiAdvert>) {
        var sortedAndFiltered = when (sharedPreferenceManager.selectedSort) {
            SortType.PriceAsc -> ads.sortedBy { it.price }
            SortType.PriceDesc -> ads.sortedByDescending { it.price }
            SortType.CityAsc -> ads.sortedBy { it.city }
            SortType.CityDesc -> ads.sortedByDescending { it.city }
            else -> ads
        }

        if (sharedPreferenceManager.selectedFilterFuel != FilterFuelType.NoFilter) {
            sortedAndFiltered = sortedAndFiltered.filter { it.fuel.lowercase() == sharedPreferenceManager.selectedFilterFuel.value }
        }
        if (sharedPreferenceManager.selectedFilterBrand != FilterBrandType.NoFilter) {
            sortedAndFiltered = sortedAndFiltered.filter { it.make.lowercase() == sharedPreferenceManager.selectedFilterBrand.value }
        }
        if (sharedPreferenceManager.selectedFilterMileage != FilterMileageType.NoFilter) {
            sortedAndFiltered = sortedAndFiltered.filter { it.mileage < sharedPreferenceManager.selectedFilterMileage.value }
        }
        if (sharedPreferenceManager.selectedFilterPrice != FilterPriceType.NoFilter) {
            sortedAndFiltered = sortedAndFiltered.filter { it.price < sharedPreferenceManager.selectedFilterPrice.value }
        }
        if (sharedPreferenceManager.selectedFilterSeller != FilterSellerType.NoFilter) {
            sortedAndFiltered = sortedAndFiltered.filter { it.sellerType?.lowercase() == sharedPreferenceManager.selectedFilterSeller.value }
        }
        adsLiveData.postValue(sortedAndFiltered)
    }

    fun searchData(keyword: String) {
        scope.launch {
            try {
                val allAds = advertDao.getAllAds()
                val dbAds = allAds.filter {
                    it.description.contains(keyword, ignoreCase = true) ||
                            it.model.contains(keyword, ignoreCase = true) ||
                            it.make.contains(keyword, ignoreCase = true) ||
                            it.city?.contains(keyword, ignoreCase = true) == true
                }
                sortFilterPostAds(dbAds.map { it.toUiAdvert() })
            } catch (exception: Exception) {
                logManager.logError(exception)
            }
        }
    }

    fun toggleBookmark(id: Int) {
        sharedPreferenceManager.toggleSavedAd(id)
    }

    fun isSavedAd(id: Int): Boolean {
        return sharedPreferenceManager.isSavedAd(id)
    }

    fun getSelectedSort(): SortType {
        return sharedPreferenceManager.selectedSort
    }

    fun setSelectedSort(sortType: SortType) {
        sharedPreferenceManager.selectedSort = sortType
    }

    fun getFilterFuel(): FilterFuelType {
        return sharedPreferenceManager.selectedFilterFuel
    }

    fun setFilterFuel(value: FilterFuelType) {
        sharedPreferenceManager.selectedFilterFuel = value
    }

    fun getFilterBrand(): FilterBrandType {
        return sharedPreferenceManager.selectedFilterBrand
    }

    fun setFilterBrand(value: FilterBrandType) {
        sharedPreferenceManager.selectedFilterBrand = value
    }

    fun getFilterMileage(): FilterMileageType {
        return sharedPreferenceManager.selectedFilterMileage
    }

    fun setFilterMileage(value: FilterMileageType) {
        sharedPreferenceManager.selectedFilterMileage = value
    }

    fun getFilterPrice(): FilterPriceType {
        return sharedPreferenceManager.selectedFilterPrice
    }

    fun setFilterPrice(value: FilterPriceType) {
        sharedPreferenceManager.selectedFilterPrice = value
    }

    fun getFilterSeller(): FilterSellerType {
        return sharedPreferenceManager.selectedFilterSeller
    }

    fun setFilterSeller(value: FilterSellerType) {
        sharedPreferenceManager.selectedFilterSeller = value
    }
}