package com.example.android.privatbank_exchange_rates.app.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.privatbank_exchange_rates.app.adapter.ExchangeRatesAdapter
import com.example.android.privatbank_exchange_rates.databinding.MainFragmentBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel by inject<MainViewModel>()
    private val exchangeRatesAdapter by inject<ExchangeRatesAdapter>()

    private var _binding: MainFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        GlobalScope.launch {
            loadExchangeRate()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getExchangeRate()
                .observe(viewLifecycleOwner) { response ->
                    exchangeRatesAdapter.removeAll()
                    response?.let {
                        binding.apply {
                            tvDateValue.text = response.date
                            tvBankValue.text = response.bank
                            tvMainCurrencyValue.text = response.baseCurrencyLit
                        }
                        response.exchangeRate.forEach { exchangeRate ->
                            exchangeRatesAdapter.add(exchangeRate)
                        }
                        exchangeRatesAdapter.notifyDataSetChanged()
                    }
                }
        }

        binding.tvDateValue.setOnClickListener {
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

    private suspend fun loadExchangeRate() {
        viewModel.loadExchangeRate()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvRates.apply {
            setHasFixedSize(true)
            adapter = exchangeRatesAdapter
            layoutManager = LinearLayoutManager(view.context)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}