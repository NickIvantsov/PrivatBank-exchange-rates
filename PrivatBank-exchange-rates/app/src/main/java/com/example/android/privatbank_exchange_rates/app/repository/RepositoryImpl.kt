package com.example.android.privatbank_exchange_rates.app.repository

import com.example.android.privatbank_exchange_rates.app.network.PrivateBankApi
import com.example.android.privatbank_exchange_rates.util.enums.FormatEnum

class RepositoryImpl(private val privateBankApi: PrivateBankApi) : IRepository {

    override suspend fun getExchangeRateByDate(date: String, format: FormatEnum) =
        privateBankApi.getExchangeRateByDate(date, format)
}