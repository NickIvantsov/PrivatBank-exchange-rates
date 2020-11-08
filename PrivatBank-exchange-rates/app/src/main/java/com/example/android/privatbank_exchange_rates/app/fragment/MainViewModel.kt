package com.example.android.privatbank_exchange_rates.app.fragment

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.privatbank_exchange_rates.app.model.ExchangeRateResponse
import com.example.android.privatbank_exchange_rates.app.repository.IRepository
import com.example.android.privatbank_exchange_rates.util.enums.FormatEnum
import com.example.android.privatbank_exchange_rates.util.errorTimber
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MainViewModel(private val repository: IRepository, dateAndTime: Calendar) : ViewModel(),
    DatePickerDialog.OnDateSetListener {
    var year = dateAndTime.get(Calendar.YEAR)
    var month = dateAndTime.get(Calendar.MONTH)
    var dayOfMonth = dateAndTime.get(Calendar.DAY_OF_MONTH)
    private val exchangeRateLiveData: MutableLiveData<ExchangeRateResponse?> by lazy {
        MutableLiveData<ExchangeRateResponse?>()
    }

    suspend fun loadExchangeRate(
        year: Int = this.year,
        month: Int = this.month,
        dayOfMonth: Int = this.dayOfMonth,
        format: FormatEnum = FormatEnum.JSON
    ) {
        try {
            val date = "$dayOfMonth.$month.$year"
            val retrievedData = repository.getExchangeRateByDate(date, format)
            withContext(Main) {
                exchangeRateLiveData.value = retrievedData
            }
        } catch (ex: Throwable) {
            errorTimber(ex)
        }
    }

    fun getExchangeRate(): LiveData<ExchangeRateResponse?> {
        return exchangeRateLiveData
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        this.year = year
        this.month = month
        this.dayOfMonth = dayOfMonth
        GlobalScope.launch {
            loadExchangeRate(year, month, dayOfMonth)
        }
    }

}