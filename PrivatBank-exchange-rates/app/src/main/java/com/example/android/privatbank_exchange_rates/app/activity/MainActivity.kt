package com.example.android.privatbank_exchange_rates.app.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.privatbank_exchange_rates.R
import com.example.android.privatbank_exchange_rates.app.repository.RepositoryImpl
import org.koin.android.ext.android.get

class MainActivity : AppCompatActivity() {

    private val repository = get<RepositoryImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}