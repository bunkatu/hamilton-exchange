package com.hamiltonch.hamiltonexchange.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.hamiltonch.hamiltonexchange.getOrAwaitValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named


@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class CurrencyDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("testDatabase")
    lateinit var database : CurrencyDB

    private lateinit var currencyDao: CurrencyDao

    @Before
    fun setup(){

        hiltRule.inject()

        currencyDao = database.currencyDao()
    }

    @After
    fun tearDown(){
        database.close()
    }


    @Test
    fun insertAllTest() = runTest {
        val usdEur = ExchangeRate("USD", "EUR", 1.0, 1)
        val usdGbp = ExchangeRate("USD", "GBP", 0.85, 2)

        currencyDao.insertAll(usdEur,usdGbp)

        val savedEur = currencyDao.observeExchangeRate("USD","EUR")
        val savedGbp = currencyDao.observeExchangeRate("USD","EUR")

        assertThat(savedEur).isNotNull()
        assertThat(savedGbp).isNotNull()
    }

    @Test
    fun deleteExchangeRatesByFromCurrencyTest() = runTest {

        val usdEur = ExchangeRate("USD", "EUR", 1.0, 1)
        val usdGbp = ExchangeRate("USD", "GBP", 0.85, 2)

        currencyDao.insertAll(usdEur,usdGbp)

        currencyDao.deleteExchangeRatesByFromCurrency("USD")

        val savedEur = currencyDao.observeExchangeRate("USD","EUR")
        val savedGbp = currencyDao.observeExchangeRate("USD","EUR")

        assertThat(savedEur).isNull()
        assertThat(savedGbp).isNull()
    }


}