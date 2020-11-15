package com.example.android.privatbank_exchange_rates.util.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.android.privatbank_exchange_rates.util.debugTimber
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CoroutineRepeatRequestWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        //todo makeRequest
        debugTimber("### Do work!")
        Result.success()
    }
}