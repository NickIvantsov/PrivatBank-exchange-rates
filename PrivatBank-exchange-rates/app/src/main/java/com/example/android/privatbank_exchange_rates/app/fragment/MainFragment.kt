package com.example.android.privatbank_exchange_rates.app.fragment

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.privatbank_exchange_rates.R
import com.example.android.privatbank_exchange_rates.app.MyApplication
import com.example.android.privatbank_exchange_rates.app.adapter.ExchangeRatesAdapter
import com.example.android.privatbank_exchange_rates.app.adapter.ExchangeRatesHeaderAdapter
import com.example.android.privatbank_exchange_rates.app.model.ExchangeRateResponse
import com.example.android.privatbank_exchange_rates.databinding.MainFragmentBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import timber.log.Timber
import kotlin.properties.Delegates

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel by inject<MainViewModel>()
    private val exchangeRatesAdapter by inject<ExchangeRatesAdapter>()
    private val exchangeRatesHeaderAdapter by inject<ExchangeRatesHeaderAdapter>()

    private var _binding: MainFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val clickDateActionObserve = Observer<Int> {
        checkInternetConnection(isConnectionAlive) {
            context?.let {
                DatePickerDialog(
                    it, viewModel,
                    viewModel.year,
                    viewModel.month,
                    viewModel.dayOfMonth
                ).show()
            }
        }
    }
    private val isConnectionAliveObserver = Observer<Boolean> { connectionChange ->
        isConnectionAlive = connectionChange
    }
    private val exchangeRateObserve = Observer<ExchangeRateResponse?> { response ->
        exchangeRatesHeaderAdapter.removeAll()
        exchangeRatesAdapter.removeAll()
        response?.let {
            exchangeRatesHeaderAdapter.add(it)
            binding.apply {
                tvDateValue.text = response.date
                tvBankValue.text = response.bank
                tvMainCurrencyValue.text = response.baseCurrencyLit
            }
            response.exchangeRate.forEach { exchangeRate ->
                exchangeRatesAdapter.add(exchangeRate)
            }
            exchangeRatesAdapter.notifyDataSetChanged()
            exchangeRatesHeaderAdapter.notifyDataSetChanged()
        }
    }

    private var isConnectionAlive: Boolean by Delegates.observable(false) { _, _, new ->
        checkInternetConnection(new) {
            makeLoadExchangeRateRequest()
        }
    }

    private fun makeLoadExchangeRateRequest() {
        GlobalScope.launch {
            loadExchangeRate()
        }
    }

    private fun showInternetErrorMsg() {
        Toast.makeText(
            context,
            getString(R.string.error_internet_connection),
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }




    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            context?.let { thisContext ->
                try {
                    val application = (thisContext.applicationContext as MyApplication)
                    application.isConnectedLiveLiveData.observe(
                        viewLifecycleOwner,
                        isConnectionAliveObserver
                    )
                } catch (ex: Throwable) {
                    Timber.e(ex)
                }
            }

            viewModel.getExchangeRate().observe(viewLifecycleOwner, exchangeRateObserve)

            exchangeRatesHeaderAdapter.getClickDateAction()
                .observe(viewLifecycleOwner, clickDateActionObserve)
        }
    }

    private fun checkInternetConnection(connectionStatus: Boolean, doWork: () -> Unit) {
        if (connectionStatus) {
            doWork()
        } else {
            showInternetErrorMsg()
        }
    }

    private suspend fun loadExchangeRate() {
        viewModel.loadExchangeRate()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val concatAdapter = ConcatAdapter(exchangeRatesHeaderAdapter, exchangeRatesAdapter)
        binding.rvRates.apply {
            setHasFixedSize(true)
            adapter = concatAdapter
            layoutManager = LinearLayoutManager(view.context)
        }
    }

    override fun onResume() {
        super.onResume()
        checkInternetConnection(isConnectionAlive) {
            makeLoadExchangeRateRequest()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}