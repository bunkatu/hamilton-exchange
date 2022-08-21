package com.hamiltonch.hamiltonexchange.api


import com.hamiltonch.hamiltonexchange.model.CurrencyResponse
import com.hamiltonch.hamiltonexchange.util.Util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitAPI {

    @GET("/v6/{key}/latest/{currency}")
    suspend fun fetchCurrency(
        @Path("currency") currency: String,
        @Path("key") apiKey: String = API_KEY
    ) : Response<CurrencyResponse>
}