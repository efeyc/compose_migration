package com.eck.compose_migration.ui.main

import com.eck.compose_migration.db.AdvertDao
import com.eck.compose_migration.helper.InstantExecutorExtension
import com.eck.compose_migration.helper.TestConstants
import com.eck.compose_migration.helper.TestConstants.adId
import com.eck.compose_migration.helper.TestConstants.apiAds
import com.eck.compose_migration.helper.TestConstants.dbAds
import com.eck.compose_migration.helper.TestConstants.dispatcher
import com.eck.compose_migration.helper.TestConstants.exception
import com.eck.compose_migration.helper.TestConstants.searchedDbAds
import com.eck.compose_migration.helper.TestConstants.searchedUiAds
import com.eck.compose_migration.helper.TestConstants.uiAds
import com.eck.compose_migration.managers.LogManager
import com.eck.compose_migration.managers.SharedPreferenceManager
import com.eck.compose_migration.network.ApiService
import com.eck.compose_migration.ui.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.never
import org.mockito.MockitoAnnotations

@ExtendWith(InstantExecutorExtension::class)
@ExperimentalCoroutinesApi
internal class MainViewModelTest {

    @Mock
    lateinit var apiService: ApiService

    @Mock
    lateinit var logManager: LogManager

    @Mock
    lateinit var advertDao: AdvertDao

    @Mock
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    private lateinit var mainViewModel: MainViewModel

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(dispatcher)

        mainViewModel = MainViewModel(apiService, logManager, sharedPreferenceManager, advertDao, dispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetch all adds, store to db, present to ui`() = runTest {

        Mockito.`when`(apiService.getAds()).thenReturn(apiAds)
        Mockito.`when`(advertDao.getAllAds()).thenReturn(dbAds)
        Mockito.`when`(sharedPreferenceManager.selectedSort).thenReturn(SortType.NoSort)
        Mockito.`when`(sharedPreferenceManager.selectedFilterBrand).thenReturn(FilterBrandType.NoFilter)
        Mockito.`when`(sharedPreferenceManager.selectedFilterFuel).thenReturn(FilterFuelType.NoFilter)
        Mockito.`when`(sharedPreferenceManager.selectedFilterMileage).thenReturn(FilterMileageType.NoFilter)
        Mockito.`when`(sharedPreferenceManager.selectedFilterPrice).thenReturn(FilterPriceType.NoFilter)
        Mockito.`when`(sharedPreferenceManager.selectedFilterSeller).thenReturn(FilterSellerType.NoFilter)

        mainViewModel.fetchData()

        Mockito.verify(advertDao).getAllAds()
        Mockito.verify(apiService).getAds()
        Mockito.verify(advertDao).insertAds(dbAds)
        Mockito.verify(logManager, never()).logError(exception)
        assertEquals(uiAds, mainViewModel.getData().value)
    }

    @Test
    fun `search ads should filter results`() = runTest {

        Mockito.`when`(advertDao.getAllAds()).thenReturn(searchedDbAds)
        Mockito.`when`(sharedPreferenceManager.selectedSort).thenReturn(SortType.NoSort)
        Mockito.`when`(sharedPreferenceManager.selectedFilterBrand).thenReturn(FilterBrandType.NoFilter)
        Mockito.`when`(sharedPreferenceManager.selectedFilterFuel).thenReturn(FilterFuelType.NoFilter)
        Mockito.`when`(sharedPreferenceManager.selectedFilterMileage).thenReturn(FilterMileageType.NoFilter)
        Mockito.`when`(sharedPreferenceManager.selectedFilterPrice).thenReturn(FilterPriceType.NoFilter)
        Mockito.`when`(sharedPreferenceManager.selectedFilterSeller).thenReturn(FilterSellerType.NoFilter)

        mainViewModel.searchData(TestConstants.searchKeyword)

        Mockito.verify(advertDao).getAllAds()
        Mockito.verify(apiService, never()).getAds()
        Mockito.verify(advertDao, never()).insertAds(dbAds)
        Mockito.verify(logManager, never()).logError(exception)
        assertEquals(searchedUiAds, mainViewModel.getData().value)
    }

    @Test
    fun `fetch all adds filtered by brand bmw`() = runTest {

        Mockito.`when`(apiService.getAds()).thenReturn(apiAds)
        Mockito.`when`(advertDao.getAllAds()).thenReturn(dbAds)
        Mockito.`when`(sharedPreferenceManager.selectedSort).thenReturn(SortType.NoSort)
        Mockito.`when`(sharedPreferenceManager.selectedFilterBrand).thenReturn(FilterBrandType.BMW)
        Mockito.`when`(sharedPreferenceManager.selectedFilterFuel).thenReturn(FilterFuelType.NoFilter)
        Mockito.`when`(sharedPreferenceManager.selectedFilterMileage).thenReturn(FilterMileageType.NoFilter)
        Mockito.`when`(sharedPreferenceManager.selectedFilterPrice).thenReturn(FilterPriceType.NoFilter)
        Mockito.`when`(sharedPreferenceManager.selectedFilterSeller).thenReturn(FilterSellerType.NoFilter)

        mainViewModel.fetchData()

        Mockito.verify(advertDao).getAllAds()
        Mockito.verify(apiService).getAds()
        Mockito.verify(advertDao).insertAds(dbAds)
        Mockito.verify(logManager, never()).logError(exception)
        assertEquals(uiAds.filter { it.make.lowercase() == FilterBrandType.BMW.value }, mainViewModel.getData().value)
    }

    @Test
    fun `fetch all adds filtered by fuel gasoline`() = runTest {

        Mockito.`when`(apiService.getAds()).thenReturn(apiAds)
        Mockito.`when`(advertDao.getAllAds()).thenReturn(dbAds)
        Mockito.`when`(sharedPreferenceManager.selectedSort).thenReturn(SortType.NoSort)
        Mockito.`when`(sharedPreferenceManager.selectedFilterBrand).thenReturn(FilterBrandType.NoFilter)
        Mockito.`when`(sharedPreferenceManager.selectedFilterFuel).thenReturn(FilterFuelType.Gasoline)
        Mockito.`when`(sharedPreferenceManager.selectedFilterMileage).thenReturn(FilterMileageType.NoFilter)
        Mockito.`when`(sharedPreferenceManager.selectedFilterPrice).thenReturn(FilterPriceType.NoFilter)
        Mockito.`when`(sharedPreferenceManager.selectedFilterSeller).thenReturn(FilterSellerType.NoFilter)

        mainViewModel.fetchData()

        Mockito.verify(advertDao).getAllAds()
        Mockito.verify(apiService).getAds()
        Mockito.verify(advertDao).insertAds(dbAds)
        Mockito.verify(logManager, never()).logError(exception)
        assertEquals(uiAds.filter { it.fuel.lowercase() == FilterFuelType.Gasoline.value }, mainViewModel.getData().value)
    }

    @Test
    fun `fetch all adds filtered by seller dealer`() = runTest {

        Mockito.`when`(apiService.getAds()).thenReturn(apiAds)
        Mockito.`when`(advertDao.getAllAds()).thenReturn(dbAds)
        Mockito.`when`(sharedPreferenceManager.selectedSort).thenReturn(SortType.NoSort)
        Mockito.`when`(sharedPreferenceManager.selectedFilterBrand).thenReturn(FilterBrandType.NoFilter)
        Mockito.`when`(sharedPreferenceManager.selectedFilterFuel).thenReturn(FilterFuelType.NoFilter)
        Mockito.`when`(sharedPreferenceManager.selectedFilterMileage).thenReturn(FilterMileageType.NoFilter)
        Mockito.`when`(sharedPreferenceManager.selectedFilterPrice).thenReturn(FilterPriceType.NoFilter)
        Mockito.`when`(sharedPreferenceManager.selectedFilterSeller).thenReturn(FilterSellerType.Dealer)

        mainViewModel.fetchData()

        Mockito.verify(advertDao).getAllAds()
        Mockito.verify(apiService).getAds()
        Mockito.verify(advertDao).insertAds(dbAds)
        Mockito.verify(logManager, never()).logError(exception)
        assertEquals(uiAds.filter { it.sellerType?.lowercase() == FilterSellerType.Dealer.value }, mainViewModel.getData().value)
    }

    @Test
    fun `fetch all adds filtered by price 10000`() = runTest {

        Mockito.`when`(apiService.getAds()).thenReturn(apiAds)
        Mockito.`when`(advertDao.getAllAds()).thenReturn(dbAds)
        Mockito.`when`(sharedPreferenceManager.selectedSort).thenReturn(SortType.NoSort)
        Mockito.`when`(sharedPreferenceManager.selectedFilterBrand).thenReturn(FilterBrandType.NoFilter)
        Mockito.`when`(sharedPreferenceManager.selectedFilterFuel).thenReturn(FilterFuelType.NoFilter)
        Mockito.`when`(sharedPreferenceManager.selectedFilterMileage).thenReturn(FilterMileageType.NoFilter)
        Mockito.`when`(sharedPreferenceManager.selectedFilterPrice).thenReturn(FilterPriceType.Until10000)
        Mockito.`when`(sharedPreferenceManager.selectedFilterSeller).thenReturn(FilterSellerType.NoFilter)

        mainViewModel.fetchData()

        Mockito.verify(advertDao).getAllAds()
        Mockito.verify(apiService).getAds()
        Mockito.verify(advertDao).insertAds(dbAds)
        Mockito.verify(logManager, never()).logError(exception)
        assertEquals(uiAds.filter { it.price < FilterPriceType.Until10000.value }, mainViewModel.getData().value)
    }

    @Test
    fun `fetch all adds filtered by mileage 50000`() = runTest {

        Mockito.`when`(apiService.getAds()).thenReturn(apiAds)
        Mockito.`when`(advertDao.getAllAds()).thenReturn(dbAds)
        Mockito.`when`(sharedPreferenceManager.selectedSort).thenReturn(SortType.NoSort)
        Mockito.`when`(sharedPreferenceManager.selectedFilterBrand).thenReturn(FilterBrandType.NoFilter)
        Mockito.`when`(sharedPreferenceManager.selectedFilterFuel).thenReturn(FilterFuelType.NoFilter)
        Mockito.`when`(sharedPreferenceManager.selectedFilterMileage).thenReturn(FilterMileageType.Until50000)
        Mockito.`when`(sharedPreferenceManager.selectedFilterPrice).thenReturn(FilterPriceType.NoFilter)
        Mockito.`when`(sharedPreferenceManager.selectedFilterSeller).thenReturn(FilterSellerType.NoFilter)

        mainViewModel.fetchData()

        Mockito.verify(advertDao).getAllAds()
        Mockito.verify(apiService).getAds()
        Mockito.verify(advertDao).insertAds(dbAds)
        Mockito.verify(logManager, never()).logError(exception)
        assertEquals(uiAds.filter { it.mileage < FilterMileageType.Until50000.value }, mainViewModel.getData().value)
    }

    @Test
    fun `fetch all adds should throw exception`() = runTest {

        Mockito.`when`(apiService.getAds()).thenThrow(exception)
        Mockito.`when`(advertDao.getAllAds()).thenReturn(dbAds)
        Mockito.`when`(sharedPreferenceManager.selectedSort).thenReturn(SortType.NoSort)
        Mockito.`when`(sharedPreferenceManager.selectedFilterBrand).thenReturn(FilterBrandType.NoFilter)
        Mockito.`when`(sharedPreferenceManager.selectedFilterFuel).thenReturn(FilterFuelType.NoFilter)
        Mockito.`when`(sharedPreferenceManager.selectedFilterMileage).thenReturn(FilterMileageType.NoFilter)
        Mockito.`when`(sharedPreferenceManager.selectedFilterPrice).thenReturn(FilterPriceType.NoFilter)
        Mockito.`when`(sharedPreferenceManager.selectedFilterSeller).thenReturn(FilterSellerType.NoFilter)

        mainViewModel.fetchData()

        Mockito.verify(advertDao).getAllAds()
        Mockito.verify(apiService).getAds()
        Mockito.verify(advertDao, never()).insertAds(dbAds)
        Mockito.verify(logManager).logError(exception)
        assertEquals(uiAds, mainViewModel.getData().value)
    }

    @Test
    fun `modify saved status of an advert when bookmark clicked`() {
        mainViewModel.toggleBookmark(adId)
        Mockito.verify(sharedPreferenceManager).toggleSavedAd(adId)
    }

    @Test
    fun `validate saved ads status`() {
        Mockito.`when`(sharedPreferenceManager.isSavedAd(adId)).thenReturn(false)

        val result = mainViewModel.isSavedAd(adId)

        Mockito.verify(sharedPreferenceManager).isSavedAd(adId)
        assertEquals(result, false)
    }

    @Test
    fun `modify sort type`() {
        mainViewModel.setSelectedSort(SortType.CityAsc)
        Mockito.verify(sharedPreferenceManager).selectedSort = SortType.CityAsc
    }

    @Test
    fun `validate sort type`() {
        Mockito.`when`(sharedPreferenceManager.selectedSort).thenReturn(SortType.PriceAsc)

        val result = mainViewModel.getSelectedSort()

        Mockito.verify(sharedPreferenceManager).selectedSort
        assertEquals(result, SortType.PriceAsc)
    }

    @Test
    fun `modify filter brand`() {
        mainViewModel.setFilterBrand(FilterBrandType.Audi)
        Mockito.verify(sharedPreferenceManager).selectedFilterBrand = FilterBrandType.Audi
    }

    @Test
    fun `validate filter brand`() {
        Mockito.`when`(sharedPreferenceManager.selectedFilterBrand).thenReturn(FilterBrandType.Ford)

        val result = mainViewModel.getFilterBrand()

        Mockito.verify(sharedPreferenceManager).selectedFilterBrand
        assertEquals(result, FilterBrandType.Ford)
    }

    @Test
    fun `modify filter fuel`() {
        mainViewModel.setFilterFuel(FilterFuelType.Diesel)
        Mockito.verify(sharedPreferenceManager).selectedFilterFuel = FilterFuelType.Diesel
    }

    @Test
    fun `validate filter fuel`() {
        Mockito.`when`(sharedPreferenceManager.selectedFilterFuel).thenReturn(FilterFuelType.Gasoline)

        val result = mainViewModel.getFilterFuel()

        Mockito.verify(sharedPreferenceManager).selectedFilterFuel
        assertEquals(result, FilterFuelType.Gasoline)
    }

    @Test
    fun `modify filter mileage`() {
        mainViewModel.setFilterMileage(FilterMileageType.NoFilter)
        Mockito.verify(sharedPreferenceManager).selectedFilterMileage = FilterMileageType.NoFilter
    }

    @Test
    fun `validate filter mileage`() {
        Mockito.`when`(sharedPreferenceManager.selectedFilterMileage).thenReturn(FilterMileageType.Until5000)

        val result = mainViewModel.getFilterMileage()

        Mockito.verify(sharedPreferenceManager).selectedFilterMileage
        assertEquals(result, FilterMileageType.Until5000)
    }

    @Test
    fun `modify filter price`() {
        mainViewModel.setFilterPrice(FilterPriceType.Until10000)
        Mockito.verify(sharedPreferenceManager).selectedFilterPrice = FilterPriceType.Until10000
    }

    @Test
    fun `validate filter price`() {
        Mockito.`when`(sharedPreferenceManager.selectedFilterPrice).thenReturn(FilterPriceType.Until25000)

        val result = mainViewModel.getFilterPrice()

        Mockito.verify(sharedPreferenceManager).selectedFilterPrice
        assertEquals(result, FilterPriceType.Until25000)
    }

    @Test
    fun `modify filter seller`() {
        mainViewModel.setFilterSeller(FilterSellerType.Dealer)
        Mockito.verify(sharedPreferenceManager).selectedFilterSeller = FilterSellerType.Dealer
    }

    @Test
    fun `validate filter seller`() {
        Mockito.`when`(sharedPreferenceManager.selectedFilterSeller).thenReturn(FilterSellerType.Private)

        val result = mainViewModel.getFilterSeller()

        Mockito.verify(sharedPreferenceManager).selectedFilterSeller
        assertEquals(result, FilterSellerType.Private)
    }

}