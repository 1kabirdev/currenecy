package com.exchangerate.services

import com.exchangerate.model.ResponseRate
import retrofit2.Response
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(private val apiService: ApiService) {

    suspend fun getLoadRates(apiKey: String, base: String): Response<ResponseRate> =
        apiService.loadRates(apiKey, base)
}