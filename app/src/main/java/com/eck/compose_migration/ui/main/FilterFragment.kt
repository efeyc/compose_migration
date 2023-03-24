package com.eck.compose_migration.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.viewModels
import com.eck.compose_migration.R
import com.eck.compose_migration.databinding.FragmentFilterBinding
import com.eck.compose_migration.ui.base.BaseBottomSheetDialogFragment
import com.eck.compose_migration.ui.models.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterFragment : BaseBottomSheetDialogFragment<FragmentFilterBinding>() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun setBinding(layoutInflater: LayoutInflater, container: ViewGroup?): FragmentFilterBinding {
        return FragmentFilterBinding.inflate(layoutInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val context = context ?: return

        binding.apply {

            binding.make.featureLabel.text = getString(R.string.make)
            initSpinner(binding.make.featureValue,
                mainViewModel.getFilterBrand().getTitle(context),
                FilterBrandType.values().map { it.getTitle(context) }) {
                mainViewModel.setFilterBrand(FilterBrandType.values()[it])
            }
            binding.mileage.featureLabel.text = getString(R.string.mileage)
            initSpinner(binding.mileage.featureValue,
                mainViewModel.getFilterMileage().getTitle(context),
                FilterMileageType.values().map { it.getTitle(context) }) {
                mainViewModel.setFilterMileage(FilterMileageType.values()[it])
            }
            binding.price.featureLabel.text = getString(R.string.price)
            initSpinner(binding.price.featureValue,
                mainViewModel.getFilterPrice().getTitle(context),
                FilterPriceType.values().map { it.getTitle(context) }) {
                mainViewModel.setFilterPrice(FilterPriceType.values()[it])
            }
            binding.fuel.featureLabel.text = getString(R.string.fuel)
            initSpinner(binding.fuel.featureValue,
                mainViewModel.getFilterFuel().getTitle(context),
                FilterFuelType.values().map { it.getTitle(context) }) {
                mainViewModel.setFilterFuel(FilterFuelType.values()[it])
            }
            binding.seller.featureLabel.text = getString(R.string.seller_type)
            initSpinner(binding.seller.featureValue,
                mainViewModel.getFilterSeller().getTitle(context),
                FilterSellerType.values().map { it.getTitle(context) }) {
                mainViewModel.setFilterSeller(FilterSellerType.values()[it])
            }

            btnClose.setOnClickListener {
                this@FilterFragment.dismiss()
            }
        }
    }

    private fun initSpinner(
        spinner: Spinner, selectedValue: String,
        items: List<String>, onSelectedItemPosition: (Int) -> Unit
    ) {
        val context = context ?: return
        ArrayAdapter(context, R.layout.item_spinner, items)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                onSelectedItemPosition(parent?.selectedItemPosition ?: 0)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // nothing to do
            }
        }
        spinner.setSelection(items.indexOf(selectedValue))
    }
}
