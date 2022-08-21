package com.hamiltonch.hamiltonexchange.repository

import androidx.lifecycle.LiveData
import com.hamiltonch.hamiltonexchange.db.ExchangeRate
import com.hamiltonch.hamiltonexchange.model.CurrencyResponse
import com.hamiltonch.hamiltonexchange.util.Resource

interface CurrencyRepositoryInterface {

    suspend fun insertExchangeRates(exchangeRates: List<ExchangeRate>)

    suspend fun deleteExchangeRates(fromCurrency: String)

    fun getExchangeRate(from: String, to: String) : LiveData<ExchangeRate>

    suspend fun fetchExchangeRates(from: String) : Resource<CurrencyResponse>

}