package com.hamiltonch.hamiltonexchange.db

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface CurrencyDao {

    @Insert
    suspend fun insertAll(vararg exchageRates: ExchangeRate)

    @Query("DELETE FROM currencies WHERE fromCurrency = :currency ")
    suspend fun deleteExchangeRatesByFromCurrency(currency: String)

    @Query("SELECT * FROM currencies WHERE fromCurrency = :from AND toCurrency = :to")
    suspend fun observeExchangeRate(from: String,to: String): ExchangeRate

}