package com.example.android.privatbank_exchange_rates.util.request

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.android.privatbank_exchange_rates.app.model.ExchangeRateResponse
import com.example.android.privatbank_exchange_rates.app.repository.IRepository
import com.example.android.privatbank_exchange_rates.util.enums.FormatEnum
import com.example.android.privatbank_exchange_rates.util.errorTimber
import kotlinx.coroutines.Dispatchers

val getExchangeRateLiveData: (String, FormatEnum, IRepository) -> LiveData<ExchangeRateResponse?> =
    { date, format, repository ->
        liveData(Dispatchers.IO) {
            try {
                val retrievedData = repository.getExchangeRateByDate(date, format)
                emit(retrievedData)
            } catch (ex: Throwable) {
                errorTimber(ex)
            }

        }
    }