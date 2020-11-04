package com.example.android.privatbank_exchange_rates.util

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.privatbank_exchange_rates.R

class ExchangeRateItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvBaseCurrency: TextView = itemView.findViewById(R.id.tv_base_currency)
    val tvCurrency: TextView = itemView.findViewById(R.id.tv_currency)
    val tvSaleRatNb: TextView = itemView.findViewById(R.id.tv_sale_rat_nb)
    val tvPurchaseRateNb: TextView = itemView.findViewById(R.id.tv_purchase_rate_nb)
    val tvSaleRate: TextView = itemView.findViewById(R.id.tv_sale_rate)
    val tvPurchaseRate: TextView = itemView.findViewById(R.id.tv_purchase_rate)

}