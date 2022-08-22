package com.hamiltonch.hamiltonexchange.repository

import com.google.gson.JsonObject
import com.hamiltonch.hamiltonexchange.db.ExchangeRate
import com.hamiltonch.hamiltonexchange.model.CurrencyResponse
import com.hamiltonch.hamiltonexchange.util.Resource
import java.util.function.Predicate

class FakeCurrencyRepository : CurrencyRepositoryInterface {

    private val currencies = mutableListOf<ExchangeRate>()


    override suspend fun insertExchangeRates(exchangeRates: List<ExchangeRate>) {
        currencies.addAll(exchangeRates)
    }

    override suspend fun deleteExchangeRates(fromCurrency: String) {
        currencies.removeIf(Predicate { exchangeRate -> exchangeRate.fromCurrency == fromCurrency })
    }

    override suspend fun getExchangeRate(from: String, to: String): ExchangeRate {
        for (currency in currencies){
            if (currency.fromCurrency == from && currency.toCurrency == to){
                return currency
            }
        }
        return ExchangeRate("USD","EUR",1.0,1)
    }

    override suspend fun fetchExchangeRates(from: String): Resource<CurrencyResponse> {
        return Resource.success(CurrencyResponse(JsonObject()))

    }
}