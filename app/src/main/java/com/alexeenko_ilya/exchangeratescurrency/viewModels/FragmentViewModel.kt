package com.alexeenko_ilya.exchangeratescurrency.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexeenko_ilya.domain.models.Currency
import com.alexeenko_ilya.domain.models.ExchangeRates
import com.alexeenko_ilya.domain.useCases.*
import com.alexeenko_ilya.exchangeratescurrency.fragments.BASE_CURRENCY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FragmentViewModel @Inject constructor(
    private val addCurrencyToFavoritesUseCase: AddCurrencyToFavoritesUseCase,
    private val deleteCurrencyFromFavoritesUseCase: DeleteCurrencyFromFavoritesUseCase,
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val getAllExchangeRatesUseCase: GetAllExchangeRatesUseCase,
    private val getExchangeRatesToFavoritesUseCase: GetExchangeRatesToFavoritesUseCase
) : ViewModel() {


    private val _currencies = MutableStateFlow(emptyList<Currency>())
    val currencies: StateFlow<List<Currency>> = _currencies

    private val _exchangeRates =
        MutableStateFlow(ExchangeRates(BASE_CURRENCY, emptyList(), emptyList()))
    val exchangeRates: StateFlow<ExchangeRates> = _exchangeRates

    fun addCurrencyToFavorites(currency: Currency) = viewModelScope.launch(Dispatchers.IO) {
        addCurrencyToFavoritesUseCase.addCurrencyToFavorites(currency)
    }

    fun deleteCurrencyFromFavorites(currency: Currency) = viewModelScope.launch(Dispatchers.IO) {
        deleteCurrencyFromFavoritesUseCase.deleteCurrencyFromFavorites(currency)
    }

    fun getCurrencies() = viewModelScope.launch(Dispatchers.IO) {
        getCurrenciesUseCase.getCurrencies().collectLatest {
            _currencies.emit(it)
        }
    }

    fun getAllExchangeRates(baseCurrency: String) = viewModelScope.launch(Dispatchers.IO) {
        getAllExchangeRatesUseCase.getAllExchangeRates(baseCurrency).collectLatest {
            _exchangeRates.emit(it)
        }
    }

    fun getExchangeRatesToFavoritesUseCase(baseCurrency: String) =
        viewModelScope.launch(Dispatchers.IO) {
            getExchangeRatesToFavoritesUseCase.getExchangeRatesToFavorites(baseCurrency)
                .collectLatest {
                    _exchangeRates.emit(it)
                }
        }
}