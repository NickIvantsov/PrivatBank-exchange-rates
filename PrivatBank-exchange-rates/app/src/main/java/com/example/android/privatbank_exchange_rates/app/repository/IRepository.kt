package com.example.android.privatbank_exchange_rates.app.repository

import com.example.android.privatbank_exchange_rates.app.model.ExchangeRateResponse
import com.example.android.privatbank_exchange_rates.util.enums.FormatEnum

interface IRepository {
    suspend fun getExchangeRateByDate(date: String,format: FormatEnum): ExchangeRateResponse?
}