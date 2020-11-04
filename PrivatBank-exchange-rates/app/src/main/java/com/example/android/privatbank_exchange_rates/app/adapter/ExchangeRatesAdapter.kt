package com.example.android.privatbank_exchange_rates.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.privatbank_exchange_rates.R
import com.example.android.privatbank_exchange_rates.app.model.ExchangeRate
import com.example.android.privatbank_exchange_rates.util.ExchangeRateItemViewHolder

class ExchangeRatesAdapter : RecyclerView.Adapter<ExchangeRateItemViewHolder>() {
   private val data = ArrayList<ExchangeRate>()

    override fun getItemCount() = data.size
    override fun onBindViewHolder(holder: ExchangeRateItemViewHolder, position: Int) {
        val item = data[position]
        holder.tvBaseCurrency.text = item.baseCurrency
        holder.tvCurrency.text = item.currency
        holder.tvPurchaseRate.text = item.purchaseRate.toString()
        holder.tvPurchaseRateNb.text = item.purchaseRate.toString()
        holder.tvSaleRate.text = item.saleRate.toString()
        holder.tvSaleRatNb.text = item.saleRateNB.toString()
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
}