package com.example.android.privatbank_exchange_rates.app.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.android.privatbank_exchange_rates.databinding.ExchangeRateItemBinding

class ExchangeRateItemViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    val binding: ExchangeRateItemBinding = ExchangeRateItemBinding.bind(itemView)
}