package com.hamiltonch.hamiltonexchange.model

import androidx.room.PrimaryKey
import java.io.Serializable
import java.text.DecimalFormat
import kotlin.math.roundToInt

data class Conversion(
    var fromCurrency : String,
    var toCurrency : String,
    var rate : Double,
    var amount : Double,
) : Serializable{

    fun calculate() : String{
        return round(amount*rate)
    }

    fun round(amount: Double) : String =
        DecimalFormat("#.00").format(amount)
//        ((amount * 100.0).roundToInt()/100.0).toString()
}