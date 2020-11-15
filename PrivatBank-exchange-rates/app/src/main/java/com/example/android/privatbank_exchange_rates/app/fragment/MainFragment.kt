package com.example.android.privatbank_exchange_rates.app.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.OneTimeWorkRequest
import androidx.work.Operation
import androidx.work.WorkManager
import com.example.android.privatbank_exchange_rates.R
import com.example.android.privatbank_exchange_rates.app.adapter.ExchangeRatesAdapter
import com.example.android.privatbank_exchange_rates.app.adapter.ExchangeRatesHeaderAdapter
import com.example.android.privatbank_exchange_rates.app.model.ExchangeRateResponse
import com.example.android.privatbank_exchange_rates.databinding.MainFragmentBinding
import com.example.android.privatbank_exchange_rates.util.debugTimber
import com.example.android.privatbank_exchange_rates.util.workers.CoroutineRepeatRequestWorker
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
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
    private val workManagerObserve = Observer<Operation.State> { operationState ->
        debugTimber("operationState = $operationState")
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

        context?.let { thisContext ->
            val myWorkRequest = OneTimeWorkRequest.from(CoroutineRepeatRequestWorker::class.java)
            val workManager = WorkManager.getInstance(thisContext).enqueue(myWorkRequest)

            workManager.state.observe(viewLifecycleOwner, workManagerObserve)
        }

        isConnectionAlive = true //todo в целях отладки необходим пересмотр

        viewLifecycleOwner.lifecycleScope.launch {

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}