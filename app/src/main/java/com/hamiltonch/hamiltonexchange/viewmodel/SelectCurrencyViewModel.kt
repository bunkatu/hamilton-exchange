package com.hamiltonch.hamiltonexchange.viewmodel

import androidx.lifecycle.*
import com.hamiltonch.hamiltonexchange.db.ExchangeRate
import com.hamiltonch.hamiltonexchange.repository.CurrencyRepositoryInterface
import com.hamiltonch.hamiltonexchange.util.CustomSharedPrefs
import com.hamiltonch.hamiltonexchange.util.Resource
import com.hamiltonch.hamiltonexchange.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectCurrencyViewModel @Inject constructor(private val repository : CurrencyRepositoryInterface) : ViewModel() {


    val exchangeRate = MutableLiveData<ExchangeRate>()

    private var calculateMsg = MutableLiveData<Resource<ExchangeRate>>()
    val calculateMessage : LiveData<Resource<ExchangeRate>>
        get() = calculateMsg

    fun resetCalculateMsg(){
        calculateMsg = MutableLiveData<Resource<ExchangeRate>>()
    }

    private var customPreferences : CustomSharedPrefs? = null
    private val REFRESH_INTERVAL = 0.1 * 60 * 1000 * 1000 * 1000L  // 10 minutes in nanoTime
    val currencyLoading = MutableLiveData<Boolean>()

    fun getData(from : String, to : String, forceApi: Boolean){
        val refreshTime = customPreferences?.getTime()
        currencyLoading.value = true
        if (!forceApi && refreshTime != null && refreshTime != 0L && System.nanoTime() - refreshTime < REFRESH_INTERVAL){
            getDataFromDB(from, to)
        }else{
            fetchFromApi(from, to)
        }

    }

    fun updateFrom(from : String){
        val current = exchangeRate.value;
        current?.let {
            current.fromCurrency = from
            exchangeRate.postValue(it)
            getData(current.fromCurrency, current.toCurrency, false)
        }
    }
    fun updateTo(to : String){
        val current = exchangeRate.value;
        current?.let {
            current.toCurrency = to
            exchangeRate.postValue(it)
            getData(current.fromCurrency, current.toCurrency, false)
        }
    }

    private fun getDataFromDB(from : String, to : String){
        viewModelScope.launch {
            val rate = repository.getExchangeRate(from, to)
            if (rate != null){
                exchangeRate.postValue(rate)
                currencyLoading.value = false
            }else{
                fetchFromApi(from, to)
            }
        }
    }


    private fun fetchFromApi(from : String, to : String){

        viewModelScope.launch {
            val response = repository.fetchExchangeRates(from)
            when(response.status){
                Status.SUCCESS -> {
                    val rates = response.data?.conversionRates?.keySet()?.map { key ->
                        ExchangeRate(from, key, response.data.conversionRates.get(key)?.asString?.toDouble() ?: 0.0)
                    }

                    repository.deleteExchangeRates(from)
                    rates?.let {
                        repository.insertExchangeRates(it)

                        val rate = repository.getExchangeRate(from, to)
                        if (rate != null){
                            exchangeRate.postValue(rate)
                        }
                    }

                    customPreferences?.saveTime(System.nanoTime())

                    currencyLoading.value = false
                }
                Status.ERROR -> {
                    currencyLoading.value = false
                }
                Status.LOADING -> {
                    currencyLoading.value = true
                }
            }
        }
    }

    fun swap(){
        val current = exchangeRate.value;
        current?.let {
            val from = current.fromCurrency
            val to = current.toCurrency
            current.toCurrency = from
            current.fromCurrency = to
            exchangeRate.postValue(it)
            getData(current.fromCurrency, current.toCurrency, false)
        }
    }

    fun calculate(amount : String){

        if (amount.isEmpty() || amount.toDouble()<=0){
            calculateMsg.value = Resource.error("Amount should be greater than zero", null)
        }else{
            calculateMsg.value = Resource.success(exchangeRate.value)
        }
    }

    fun setPreferences(prefs : CustomSharedPrefs){
        customPreferences = prefs
    }
}