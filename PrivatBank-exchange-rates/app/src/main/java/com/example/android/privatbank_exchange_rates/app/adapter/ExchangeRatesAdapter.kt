package com.example.android.privatbank_exchange_rates.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.privatbank_exchange_rates.R
import com.example.android.privatbank_exchange_rates.app.model.ExchangeRate

class ExchangeRatesAdapter : RecyclerView.Adapter<ExchangeRateItemViewHolder>() {
    private val data = ArrayList<ExchangeRate>()

    override fun getItemCount() = data.size
    override fun onBindViewHolder(holder: ExchangeRateItemViewHolder, position: Int) {
        val item = data[position]
        holder.binding.apply {
            item.apply {
                tvBaseCurrencyValue.text = baseCurrency
                tvCurrencyValue.text = currency
                tvPurchaseRateValue.text = purchaseRate.toString()
                tvPurchaseRateNbValue.text = purchaseRateNB.toString()
                tvSaleRateValue.text = saleRate.toString()
                tvSaleRatNbValue.text = saleRateNB.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ExchangeRateItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View =
            inflater.inflate(R.layout.exchange_rate_item, parent, false)
        return ExchangeRateItemViewHolder(view)
    }

    fun add(exchangeRate: ExchangeRate) {
        data.forEach {
            when (it) {
                exchangeRate -> {
                    return
                }
            }
        }
        data.add(exchangeRate)
        notifyItemInserted(data.size - 1)
    }

    fun removeAll() {
        data.clear()
    }
}