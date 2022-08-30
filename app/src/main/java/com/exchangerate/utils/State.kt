package com.exchangerate.utils

import com.exchangerate.model.Rates
import com.exchangerate.model.WishlistDao

sealed class PopularState {
    object Loading : PopularState()
    class Failure(val msg: Throwable) : PopularState()
    class Success(val data: Rates) : PopularState()
    object Empty : PopularState()
}

sealed class AddWishlistPopularState {
    object Success : AddWishlistPopularState()
    object Empty : AddWishlistPopularState()
}

sealed class WishlistState {
    class Success(val wishlistDao: ArrayList<WishlistDao>) : WishlistState()
    object Empty : WishlistState()
}

sealed class WishlistDeleteState {
    object Success : WishlistDeleteState()
    object Empty : WishlistDeleteState()
}