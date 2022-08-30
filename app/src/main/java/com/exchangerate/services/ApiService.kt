package com.exchangerate.services

import com.exchangerate.model.ResponseRate
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("latest")
    suspend fun loadRates(
        @Query("apikey") apikey: String,
    ): Response<ResponseRate>
}