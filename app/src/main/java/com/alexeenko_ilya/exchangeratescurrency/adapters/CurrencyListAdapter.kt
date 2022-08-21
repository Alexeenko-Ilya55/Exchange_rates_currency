package com.alexeenko_ilya.exchangeratescurrency.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexeenko_ilya.domain.models.Currency
import com.alexeenko_ilya.domain.models.ExchangeRates
import com.alexeenko_ilya.exchangeratescurrency.R
import com.alexeenko_ilya.exchangeratescurrency.databinding.CurrencyItemLayoutBinding
import com.alexeenko_ilya.exchangeratescurrency.fragments.BASE_CURRENCY

class CurrencyListAdapter(private val clickListener: CurrenciesRecyclerViewClickListener) :
    RecyclerView.Adapter<CurrencyListAdapter.CurrencyListHolder>() {

    private lateinit var exchangeRates: ExchangeRates

    fun setData(exchangeRates: ExchangeRates) {
        this.exchangeRates = exchangeRates
        notifyDataSetChanged()
    }

    fun setData(map: Map<Currency, Double>) {
        exchangeRates = ExchangeRates(BASE_CURRENCY, map.keys.toList(), map.values.toList())
        notifyDataSetChanged()
    }

    class CurrencyListHolder(private val binding: CurrencyItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val favoritesButton = binding.favoritesButton
        fun bind(
            currencyName: String,
            exchangeRates: Double?,
            isFavorite: Boolean
        ) {
            binding.currencyName.text = currencyName
            binding.exchangeRate.text = exchangeRates.toString()
            changeImageFavoriteButton(isFavorite)
        }

        fun changeImageFavoriteButton(isFavorite: Boolean) {
            if (isFavorite)
                binding.favoritesButton.setImageResource(R.drawable.favorite_button_enable)
            else
                binding.favoritesButton.setImageResource(R.drawable.favorite_button_not_enable)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CurrencyListHolder(
            CurrencyItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: CurrencyListHolder, position: Int) {
        val currency = exchangeRates.currencies[position]
        holder.bind(
            currency.code,
            exchangeRates.values[position],
            currency.isFavorite,
        )
        holder.favoritesButton.setOnClickListener {
            currency.isFavorite = !currency.isFavorite
            clickListener.buttonFavoriteClicked(currency.code, currency.isFavorite)
            holder.changeImageFavoriteButton(currency.isFavorite)
        }
    }

    override fun getItemCount() = exchangeRates.values.size
}

interface CurrenciesRecyclerViewClickListener {
    fun buttonFavoriteClicked(currencyCode: String, isFavorite: Boolean)
}
