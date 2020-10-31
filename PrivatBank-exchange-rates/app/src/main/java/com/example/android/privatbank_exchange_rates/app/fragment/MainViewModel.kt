package com.example.android.privatbank_exchange_rates.app.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.android.privatbank_exchange_rates.app.model.ExchangeRateResponse
import com.example.android.privatbank_exchange_rates.app.repository.IRepository
import com.example.android.privatbank_exchange_rates.util.enums.FormatEnum
import kotlinx.coroutines.Dispatchers

class MainViewModel(repository: IRepository) : ViewModel() {

    val dataLiveData: (String, FormatEnum) -> LiveData<ExchangeRateResponse?> = { date, format ->
        liveData(Dispatchers.IO) {
            val retrievedData = repository.getExchangeRateByDate(date, format)
            emit(retrievedData)
        }
    }
}