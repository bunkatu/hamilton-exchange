package com.hamiltonch.hamiltonexchange.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hamiltonch.hamiltonexchange.model.Conversion

class ExchangeViewModel : ViewModel() {
    private var countDownTimer : CountDownTimer? = null

    private var conversionData = MutableLiveData<Conversion>()
    val conversion : LiveData<Conversion>
        get() = conversionData

    private var durationData = MutableLiveData<Int>()
    val duration : LiveData<Int>
        get() = durationData

    private var expiredData = MutableLiveData<Boolean>()
    val expired : LiveData<Boolean>
        get() = expiredData

    fun setConversion(conv : Conversion){
        conversionData.value = conv
    }
    fun clearExpired(){
        if (countDownTimer != null){
            countDownTimer?.cancel()
        }
        expiredData.value = false
    }

    fun startTimer(){
        if (countDownTimer != null){
            countDownTimer?.cancel()
        }
        countDownTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var diff = millisUntilFinished
                val secondsInMilli: Long = 1000
                val seconds = diff / secondsInMilli
                durationData.value = seconds.toInt()
            }

            override fun onFinish() {
                expiredData.value = true
            }

        }.start()
    }

}