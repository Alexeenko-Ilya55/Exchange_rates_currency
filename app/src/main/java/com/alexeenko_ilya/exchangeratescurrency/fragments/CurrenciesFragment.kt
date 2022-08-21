package com.alexeenko_ilya.exchangeratescurrency.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexeenko_ilya.domain.models.Currency
import com.alexeenko_ilya.exchangeratescurrency.R
import com.alexeenko_ilya.exchangeratescurrency.adapters.CurrenciesRecyclerViewClickListener
import com.alexeenko_ilya.exchangeratescurrency.adapters.CurrencyListAdapter
import com.alexeenko_ilya.exchangeratescurrency.databinding.CurrencyFragmentBinding
import com.alexeenko_ilya.exchangeratescurrency.viewModels.FragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.set

const val BASE_CURRENCY = "USD"
const val ARG_KEY = "typeFragment"
const val FRAGMENT_FAVORITES = "favorites"
const val LAST_VIEWED_CURRENCY_KEY = "Last viewed currency"

@AndroidEntryPoint
class AllCurrencyFragment : Fragment(), CurrenciesRecyclerViewClickListener {

    private val viewModel: FragmentViewModel by viewModels()

    @Inject
    lateinit var dataStore: DataStore<Preferences>
    private var exchangeRatesMap: MutableMap<Currency, Double> = mutableMapOf()
    private lateinit var binding: CurrencyFragmentBinding
    private val lastViewedCurrencyKey = stringPreferencesKey(LAST_VIEWED_CURRENCY_KEY)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CurrencyFragmentBinding.inflate(inflater, container, false)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = CurrencyListAdapter(this)
        binding.recyclerView.adapter = adapter

        val popupMenu = PopupMenu(requireContext(), binding.sortingButton)
        popupMenu.inflate(R.menu.popup_menu)
        popupMenu.setOnMenuItemClickListener { item ->
            popupMenuItemClicked(item, adapter)
            return@setOnMenuItemClickListener true
        }
        binding.sortingButton.setOnClickListener { popupMenu.show() }

        viewModel.getCurrencies()

        lifecycleScope.launchWhenStarted {
            viewModel.currencies.collectLatest {
                initSpinnerAdapter(it.map { currency -> currency.code })
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.exchangeRates.collectLatest {
                adapter.setData(it)
                it.currencies.forEachIndexed { index, currency ->
                    exchangeRatesMap[currency] = it.values[index]
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.exceptionHandler.collectLatest {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }
        return binding.root
    }

    private fun popupMenuItemClicked(item: MenuItem, adapter: CurrencyListAdapter) {
        when (item.itemId) {
            R.id.alphabetAscending -> adapter.setData(
                exchangeRatesMap.toList().sortedBy { it.first.code }.toMap()
            )
            R.id.alphabetDescending -> adapter.setData(
                exchangeRatesMap.toList().sortedByDescending { it.first.code }.toMap()
            )
            R.id.valueAscending -> adapter.setData(exchangeRatesMap.toList().sortedBy { it.second }
                .toMap())
            R.id.valueDescending -> adapter.setData(
                exchangeRatesMap.toList().sortedByDescending { it.second }.toMap()
            )
        }
        item.isChecked = true
    }

    private fun initSpinnerAdapter(currencies: List<String>) {
        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            currencies
        )
        binding.spinner.adapter = spinnerAdapter
        lifecycleScope.launch {
            binding.spinner.setSelection(currencies.indexOf(readFromDataStore()))
        }
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                if (arguments?.getString(ARG_KEY).equals(FRAGMENT_FAVORITES))
                    viewModel.getExchangeRatesToFavoritesUseCase(currencies[position])
                else
                    viewModel.getAllExchangeRates(currencies[position])
                lifecycleScope.launch { saveToDataStore(currencies[position]) }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    override fun buttonFavoriteClicked(currencyCode: String, isFavorite: Boolean) {
        if (isFavorite) {
            viewModel.addCurrencyToFavorites(Currency(currencyCode, isFavorite))
        } else {
            viewModel.deleteCurrencyFromFavorites(Currency(currencyCode, isFavorite))
        }
    }

    private suspend fun saveToDataStore(currencyCode: String) = dataStore.edit { preferences ->
        preferences[lastViewedCurrencyKey] = currencyCode
    }

    private suspend fun readFromDataStore(): String =
        dataStore.data.first()[lastViewedCurrencyKey] ?: BASE_CURRENCY
}