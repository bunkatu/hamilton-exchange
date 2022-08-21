package com.hamiltonch.hamiltonexchange.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.hamiltonch.hamiltonexchange.R
import com.hamiltonch.hamiltonexchange.databinding.FragmentSelectCurrencyBinding
import com.hamiltonch.hamiltonexchange.view.adapter.CurrencyRecyclerAdapter
import javax.inject.Inject

class SelectCurrencyFragment @Inject constructor(val currencyRecyclerAdapter: CurrencyRecyclerAdapter)  : Fragment() {


    private lateinit var binding : FragmentSelectCurrencyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_currency, container, false)
        binding.listener = View.OnClickListener {
            findNavController().navigate(SelectCurrencyFragmentDirections.actionSelectCurrencyFragmentToExchangeFragment("USD","EUR","150"))
        }


        return binding.root
    }

    fun onButtonClick(){

    }

}