package com.exchangerate.repository

import com.exchangerate.model.WishlistDao
import com.exchangerate.room.AppDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WishlistRepository
@Inject constructor(private var appDao: AppDao) {
    fun loadWishlist(): Flow<ArrayList<WishlistDao>> = flow {
        emit(appDao.getAll() as ArrayList)
    }.flowOn(Dispatchers.IO)

    fun deleteWishlist(wishlistDao: WishlistDao) = flow {
        emit(appDao.delete(wishlistDao))
    }.flowOn(Dispatchers.IO)
}