package com.hamiltonch.hamiltonexchange.repository


import androidx.lifecycle.LiveData
import com.hamiltonch.hamiltonexchange.api.RetrofitAPI
import com.hamiltonch.hamiltonexchange.db.CurrencyDao
import com.hamiltonch.hamiltonexchange.db.ExchangeRate
import com.hamiltonch.hamiltonexchange.model.CurrencyResponse
import com.hamiltonch.hamiltonexchange.util.Resource
import java.lang.Exception
import javax.inject.Inject

class CurrencyRepository @Inject constructor(private val currencyDao: CurrencyDao, private val retrofitAPI: RetrofitAPI) : CurrencyRepositoryInterface {

    override suspend fun insertExchangeRates(exchangeRates: List<ExchangeRate>) {
        currencyDao.insertAll(*exchangeRates.toTypedArray())
    }

    override suspend fun deleteExchangeRates(fromCurrency: String) {
        currencyDao.deleteExchangeRatesByFromCurrency(fromCurrency)
    }

    override suspend fun getExchangeRate(from: String, to: String): ExchangeRate {
        return currencyDao.observeExchangeRate(from, to)
    }

    override suspend fun fetchExchangeRates(from: String): Resource<CurrencyResponse> {
        return try {
            val response = retrofitAPI.fetchCurrency(from)
            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            } else {
                Resource.error("Error", null)
            }
        }catch (e: Exception){
            println(e.message)
            Resource.error("No data!", null)
        }
    }


}