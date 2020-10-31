package com.example.android.privatbank_exchange_rates.app.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.android.privatbank_exchange_rates.app.model.ExchangeRateResponse
import com.example.android.privatbank_exchange_rates.app.repository.IRepository
import com.example.android.privatbank_exchange_rates.util.enums.FormatEnum
import com.example.android.privatbank_exchange_rates.util.request.getExchangeRateLiveData

class MainViewModel(private val repository: IRepository) : ViewModel() {

    fun getExchangeRate(date: String, format: FormatEnum): LiveData<ExchangeRateResponse?> =
        getExchangeRateLiveData(date, format, repository)
}