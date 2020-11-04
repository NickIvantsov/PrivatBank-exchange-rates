package com.example.android.privatbank_exchange_rates.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.privatbank_exchange_rates.R
import com.example.android.privatbank_exchange_rates.app.adapter.ExchangeRatesAdapter
import com.example.android.privatbank_exchange_rates.app.model.ExchangeRate
import com.example.android.privatbank_exchange_rates.util.enums.FormatEnum
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel by inject<MainViewModel>()
    lateinit var exchangeRatesAdapter: ExchangeRatesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        exchangeRatesAdapter = ExchangeRatesAdapter()
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getExchangeRate("01.12.2014", FormatEnum.JSON)
                .observe(viewLifecycleOwner) {
                    it?.exchangeRate?.forEach { exchangeRate ->
                        exchangeRatesAdapter.add(exchangeRate)
                    }
                    exchangeRatesAdapter.notifyDataSetChanged()
                    //tv_answer.text = it.toString()
                }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_rates.apply {
            setHasFixedSize(true)
            adapter = exchangeRatesAdapter
            layoutManager = LinearLayoutManager(view.context)
        }
    }
}