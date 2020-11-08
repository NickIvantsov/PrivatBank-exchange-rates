package com.example.android.privatbank_exchange_rates.app.di

import com.example.android.privatbank_exchange_rates.app.adapter.ExchangeRatesAdapter
import com.example.android.privatbank_exchange_rates.app.fragment.MainViewModel
import com.example.android.privatbank_exchange_rates.app.network.PRIVATE_BANK_URL
import com.example.android.privatbank_exchange_rates.app.network.PrivateBankApi
import com.example.android.privatbank_exchange_rates.app.repository.RepositoryImpl
import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


val appMode = module {
    single {
        val interceptor = HttpLoggingInterceptor()
        OkHttpClient().newBuilder()
            .addInterceptor(interceptor)
            .addInterceptor(OkHttpProfilerInterceptor())
            .build()
    }
    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(PRIVATE_BANK_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }
    single {
        get<Retrofit>().create(PrivateBankApi::class.java)
    }
    single {
        Calendar.getInstance()
    }
    factory {
        ExchangeRatesAdapter()
    }
}
val repositoryModule = module {
    single { RepositoryImpl(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get<RepositoryImpl>(), get()) }
}
