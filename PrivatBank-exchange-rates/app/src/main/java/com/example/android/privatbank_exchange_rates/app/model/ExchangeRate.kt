package com.example.android.privatbank_exchange_rates.app.model


import com.google.gson.annotations.SerializedName

data class ExchangeRate(
    @SerializedName("baseCurrency")
    val baseCurrency: String = "",
    @SerializedName("currency")
    val currency: String = "",
    @SerializedName("purchaseRate")
    val purchaseRate: Double = 0.0,
    @SerializedName("purchaseRateNB")
    val purchaseRateNB: Double = 0.0,
    @SerializedName("saleRate")
    val saleRate: Double = 0.0,
    @SerializedName("saleRateNB")
    val saleRateNB: Double = 0.0
)