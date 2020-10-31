package com.example.android.privatbank_exchange_rates.app.network

import com.example.android.privatbank_exchange_rates.app.model.ExchangeRateResponse
import com.example.android.privatbank_exchange_rates.util.enums.FormatEnum
import retrofit2.http.GET
import retrofit2.http.Query

interface PrivateBankApi {
    @GET("exchange_rates?json")
    suspend fun getExchangeRateByDate(@Query("date") date: String,@Query("format") format: FormatEnum): ExchangeRateResponse?
}