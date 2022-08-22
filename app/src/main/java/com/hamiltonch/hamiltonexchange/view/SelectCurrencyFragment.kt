package com.hamiltonch.hamiltonexchange.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.hamiltonch.hamiltonexchange.BuildConfig
import com.hamiltonch.hamiltonexchange.R
import com.hamiltonch.hamiltonexchange.databinding.FragmentSelectCurrencyBinding
import com.hamiltonch.hamiltonexchange.model.Conversion
import com.hamiltonch.hamiltonexchange.util.CustomSharedPrefs
import com.hamiltonch.hamiltonexchange.util.Status
import com.hamiltonch.hamiltonexchange.view.adapter.CurrencyRecyclerAdapter
import com.hamiltonch.hamiltonexchange.viewmodel.SelectCurrencyViewModel
import javax.inject.Inject

class SelectCurrencyFragment @Inject constructor(val currencyRecyclerAdapter: CurrencyRecyclerAdapter)  : Fragment() {


    private lateinit var binding : FragmentSelectCurrencyBinding
    lateinit var viewModel : SelectCurrencyViewModel

    private var currencies = BuildConfig.SUPPORTED_CURRENCIES.toList()

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private var currentSelectionFrom = false

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
        binding.callback = object : SelectCurrencyCallback{
            override fun onFromCurrency(view: View) {
                showCurrencyPicker(true)
            }

            override fun onToCurrency(view: View) {
                showCurrencyPicker(false)
            }

            override fun onCalculate(view: View) {
                viewModel.calculate(binding.amountInput.text.toString())
            }

            override fun onChangeBtn(view: View) {
                viewModel.swap()
            }

        }

        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // handle onSlide
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {

            }
        })




        return binding.root
    }

    private fun showCurrencyPicker(isFrom : Boolean){
        currentSelectionFrom = isFrom
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(SelectCurrencyViewModel::class.java)
        viewModel.setPreferences(CustomSharedPrefs(requireContext().applicationContext))

        if (!viewModel.currenciesSet){
            viewModel.getData(currencies[0], currencies[1],false)
        }

        binding.currenciesRecycler.adapter = currencyRecyclerAdapter
        binding.currenciesRecycler.layoutManager = LinearLayoutManager(context)
        currencyRecyclerAdapter.setCurrencyList(currencies)

        currencyRecyclerAdapter.setOnItemClickListener {
            if (currentSelectionFrom){
                viewModel.updateFrom(it)
            }else{
                viewModel.updateTo(it)
            }
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

        observeLiveData()
    }


    private fun observeLiveData(){
        viewModel.exchangeRate.observe(viewLifecycleOwner, Observer{currency ->
            currency?.let {
                binding.exchangeRate = it
            }
        })

        viewModel.currencyLoading.observe(viewLifecycleOwner, Observer{ loading ->
            loading?.let {
                binding.isLoading = loading
            }

        })

        viewModel.calculateMessage.observe(viewLifecycleOwner, Observer{
            when(it.status){
                Status.SUCCESS -> {
                    findNavController().navigate(SelectCurrencyFragmentDirections.actionSelectCurrencyFragmentToExchangeFragment(
                        Conversion(it.data?.fromCurrency?:"",it.data?.toCurrency?:"", it.data?.rate?:1.0,  binding.amountInput.text.toString().toDouble()))
                    )
//                    binding.amountInput.setText("")
                    viewModel.resetCalculateMsg()
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message ?: "Error!", Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {

                }
            }
        })
    }


    interface SelectCurrencyCallback {
        fun onFromCurrency(view: View)
        fun onToCurrency(view: View)
        fun onCalculate(view: View)
        fun onChangeBtn(view: View)
    }


}