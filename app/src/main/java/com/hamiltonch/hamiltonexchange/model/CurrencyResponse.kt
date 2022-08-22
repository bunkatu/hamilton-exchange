package com.hamiltonch.hamiltonexchange.model

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class CurrencyResponse(
    @SerializedName("conversion_rates")
    val conversionRates: JsonObject
)