package com.hamiltonch.hamiltonexchange.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.hamiltonch.hamiltonexchange.MainCoroutineRule
import com.hamiltonch.hamiltonexchange.getOrAwaitValueTest
import com.hamiltonch.hamiltonexchange.repository.FakeCurrencyRepository
import com.hamiltonch.hamiltonexchange.util.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SelectCurrencyViewModelTest {
    private lateinit var viewModel: SelectCurrencyViewModel


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup(){
        viewModel = SelectCurrencyViewModel(FakeCurrencyRepository())
    }


    @Test
    fun `calculate exchange without amount returns error`(){
        viewModel.calculate("")
        val value = viewModel.calculateMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `calculate exchange with amount lte zero`(){
        viewModel.calculate("-1")
        val value = viewModel.calculateMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `calculate exchange with amount returns success`(){
        viewModel.calculate("100")
        val value = viewModel.calculateMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.SUCCESS)
    }



}