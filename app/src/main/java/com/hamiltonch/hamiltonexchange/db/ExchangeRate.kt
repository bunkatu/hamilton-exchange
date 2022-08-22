package com.hamiltonch.hamiltonexchange.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DecimalFormat

@Entity(tableName = "currencies")
data class ExchangeRate(
    var fromCurrency : String,
    var toCurrency : String,
    var rate : Double,
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null
){

}