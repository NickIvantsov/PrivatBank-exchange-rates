package com.example.android.privatbank_exchange_rates.app.network

import androidx.annotation.UiThread
import com.example.android.privatbank_exchange_rates.app.model.ExchangeRateResponse
import com.example.android.privatbank_exchange_rates.util.enums.FormatEnum
import retrofit2.http.GET
import retrofit2.http.Query

const val PRIVATE_BANK_URL = "https://api.privatbank.ua/p24api/"

interface PrivateBankApi {
    @GET("exchange_rates?json")
    @UiThread
    suspend fun getExchangeRateByDate(@Query("date") date: String,@Query("format") format: FormatEnum): ExchangeRateResponse?
}