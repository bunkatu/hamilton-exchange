package com.hamiltonch.hamiltonexchange.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.hamiltonch.hamiltonexchange.view.adapter.CurrencyRecyclerAdapter
import javax.inject.Inject

class ExchangeFragmentFactory @Inject constructor(
    private val currencyRecyclerAdapter: CurrencyRecyclerAdapter) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when(className) {
            SelectCurrencyFragment::class.java.name -> SelectCurrencyFragment(currencyRecyclerAdapter)
            ExchangeFragment::class.java.name -> ExchangeFragment()
            ResultFragment::class.java.name -> ResultFragment()
            else -> return super.instantiate(classLoader, className)
        }
    }

}

