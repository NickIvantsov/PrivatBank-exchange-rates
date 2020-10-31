package com.example.android.privatbank_exchange_rates.app.model


import com.google.gson.annotations.SerializedName

data class ExchangeRateResponse(
    @SerializedName("bank")
    val bank: String = "",
    @SerializedName("baseCurrency")
    val baseCurrency: Int = 0,
    @SerializedName("baseCurrencyLit")
    val baseCurrencyLit: String = "",
    @SerializedName("date")
    val date: String = "",
    @SerializedName("exchangeRate")
    val exchangeRate: List<ExchangeRate> = listOf()
)