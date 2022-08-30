package com.exchangerate.repository

import com.exchangerate.model.ResponseRate
import com.exchangerate.model.WishlistDao
import com.exchangerate.room.AppDao
import com.exchangerate.services.ApiServiceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class PopularRepository
@Inject
constructor(
    private val apiServiceImpl: ApiServiceImpl,
    private var appDao: AppDao
) {

    fun getLoadRatesList(apiKey: String): Flow<Response<ResponseRate>> = flow {
        emit(apiServiceImpl.getLoadRates(apiKey))
    }.flowOn(Dispatchers.IO)

    fun addWishlistCurrency(wishlistDao: WishlistDao) = flow {
        emit(appDao.insert(wishlistDao))
    }.flowOn(Dispatchers.IO)

}