package com.alexeenko_ilya.exchangeratescurrency.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexeenko_ilya.domain.models.Currency
import com.alexeenko_ilya.domain.models.ExchangeRates
import com.alexeenko_ilya.domain.useCases.*
import com.alexeenko_ilya.exchangeratescurrency.R
import com.alexeenko_ilya.exchangeratescurrency.fragments.BASE_CURRENCY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

const val INTERNET_EXCEPTION = R.string.internetException
const val TAG_EXCEPTION = "Exception"

@HiltViewModel
class FragmentViewModel @Inject constructor(
    private val addCurrencyToFavoritesUseCase: AddCurrencyToFavoritesUseCase,
    private val deleteCurrencyFromFavoritesUseCase: DeleteCurrencyFromFavoritesUseCase,
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val getAllExchangeRatesUseCase: GetAllExchangeRatesUseCase,
    private val getExchangeRatesToFavoritesUseCase: GetExchangeRatesToFavoritesUseCase
) : ViewModel() {

    private val _exceptionHandler = MutableSharedFlow<Int>(1)
    val exceptionHandler: SharedFlow<Int> = _exceptionHandler

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
        try {
            getCurrenciesUseCase.getCurrencies().collectLatest {
                _currencies.emit(it)
            }
        } catch (e: Exception){
            _exceptionHandler.emit(INTERNET_EXCEPTION)
        }
    }

    fun getAllExchangeRates(baseCurrency: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            getAllExchangeRatesUseCase.getAllExchangeRates(baseCurrency).collectLatest {
                _exchangeRates.emit(it)
            }
        } catch (e: Exception) {
            Log.i(TAG_EXCEPTION, e.message.toString())
        }
    }

    fun getExchangeRatesToFavoritesUseCase(baseCurrency: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            getExchangeRatesToFavoritesUseCase.getExchangeRatesToFavorites(baseCurrency)
                .collectLatest {
                    _exchangeRates.emit(it)
                }
        } catch (e: Exception) {
            Log.i(TAG_EXCEPTION, e.message.toString())
        }
    }
}