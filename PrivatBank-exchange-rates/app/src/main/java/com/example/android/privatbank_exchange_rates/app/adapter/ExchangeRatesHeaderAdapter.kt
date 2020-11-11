package com.example.android.privatbank_exchange_rates.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.android.privatbank_exchange_rates.R
import com.example.android.privatbank_exchange_rates.app.model.ExchangeRateResponse

class ExchangeRatesHeaderAdapter : RecyclerView.Adapter<ExchangeRateHeaderItemViewHolder>() {

    private val data = ArrayList<ExchangeRateResponse>()
    private val clickDate = MutableLiveData<Int>()

    override fun getItemCount() = data.size
    override fun onBindViewHolder(holder: ExchangeRateHeaderItemViewHolder, position: Int) {
        if (data.isNotEmpty()) {
            val item = data[position]
            holder.binding.apply {
                item.apply {
                    tvDateValue.text = date
                    tvBankValue.text = bank
                    tvMainCurrencyValue.text = baseCurrencyLit
                }
                tvDateValue.setOnClickListener {
                    clickDate.value = 0
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ExchangeRateHeaderItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View =
            inflater.inflate(R.layout.exchange_rate_header_item, parent, false)
        return ExchangeRateHeaderItemViewHolder(view)
    }

    fun add(exchangeRate: ExchangeRateResponse) {
        data.forEach {
            when (it) {
                exchangeRate -> {
                    return
                }
            }
        }
        data.add(exchangeRate)
        notifyItemInserted(data.size )
    }

    fun getClickDateAction(): MutableLiveData<Int> = clickDate
    fun removeAll() {
        data.clear()
    }
}