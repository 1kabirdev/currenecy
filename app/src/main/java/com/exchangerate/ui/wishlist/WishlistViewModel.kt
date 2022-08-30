package com.exchangerate.ui.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exchangerate.model.WishlistDao
import com.exchangerate.repository.WishlistRepository
import com.exchangerate.utils.WishlistDeleteState
import com.exchangerate.utils.WishlistState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel
@Inject constructor(
    private var wishlistRepository: WishlistRepository
) : ViewModel() {

    private val _wishlistStateFlow: MutableStateFlow<WishlistState> =
        MutableStateFlow(WishlistState.Empty)
    val wishlistStateFlow: StateFlow<WishlistState> = _wishlistStateFlow

    private val _wishlistDeleteStateFlow: MutableStateFlow<WishlistDeleteState> =
        MutableStateFlow(WishlistDeleteState.Empty)
    val wishlistDeleteStateFlow: StateFlow<WishlistDeleteState> = _wishlistDeleteStateFlow

    fun getLoadWishlist() = viewModelScope.launch {
        wishlistRepository.loadWishlist().collect { data ->
            _wishlistStateFlow.value = WishlistState.Success(data)
        }
    }

    fun deleteWishlist(wishlistDao: WishlistDao) = viewModelScope.launch {
        wishlistRepository.deleteWishlist(wishlistDao).collect {
            _wishlistDeleteStateFlow.value = WishlistDeleteState.Success
        }
    }
}