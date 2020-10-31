package com.example.android.privatbank_exchange_rates.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.android.privatbank_exchange_rates.R
import com.example.android.privatbank_exchange_rates.util.enums.FormatEnum
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel by inject<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getExchangeRate("01.12.2014", FormatEnum.JSON)
                .observe(viewLifecycleOwner) {
                    tv_answer.text = it.toString()
                }
        }
    }

}