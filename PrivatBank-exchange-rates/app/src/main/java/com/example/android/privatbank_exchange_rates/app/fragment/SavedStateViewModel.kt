package com.example.android.privatbank_exchange_rates.app.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.android.privatbank_exchange_rates.app.model.ExchangeRateResponse
import com.example.android.privatbank_exchange_rates.util.debugTimber


class SavedStateViewModel(state: SavedStateHandle) : ViewModel() {
    // Keep the key as a constant
    companion object {
        private val EXCHANGE_RATE = "exchange_rate"
    }

    private val savedStateHandle = state

    fun saveCurrentUser(exchangeRate: ExchangeRateResponse) {
        debugTimber("saveCurrentUser")
        // Sets a new value for the object associated to the key.
        savedStateHandle.set(EXCHANGE_RATE, exchangeRate)
    }

    fun getExchangeRate(): ExchangeRateResponse? {
        // Gets the current value of the user id from the saved state handle
        return savedStateHandle.get(EXCHANGE_RATE)
    }

    // Expose an immutable LiveData
    fun getExchangeRateLiveData(): LiveData<ExchangeRateResponse?>? {
        debugTimber("getExchangeRateLiveData")
        return savedStateHandle.getLiveData(EXCHANGE_RATE)
    }
}