package com.exchangerate.ui.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exchangerate.model.WishlistDao
import com.exchangerate.repository.PopularRepository
import com.exchangerate.utils.AddWishlistPopularState
import com.exchangerate.utils.PopularState
import com.exchangerate.utils.WishlistDeleteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularViewModel
@Inject
constructor(
    private val mainRepository: PopularRepository
) : ViewModel() {

    private val _popularStateFlow: MutableStateFlow<PopularState> =
        MutableStateFlow(PopularState.Empty)
    val popularStateFlow: StateFlow<PopularState> = _popularStateFlow

    private val _popularAddWishlistStateFlow: MutableStateFlow<AddWishlistPopularState> =
        MutableStateFlow(AddWishlistPopularState.Empty)
    val popularAddWishlistStateFlow: StateFlow<AddWishlistPopularState> =
        _popularAddWishlistStateFlow

    fun getLoadRateList(apiKey: String, base: String) = viewModelScope.launch {
        _popularStateFlow.value = PopularState.Loading
        mainRepository.getLoadRatesList(apiKey, base)
            .catch { e ->
                _popularStateFlow.value = PopularState.Failure(e)
            }.collect { data ->
                if (data.isSuccessful) {
                    data.body()?.let { dataRates ->
                        _popularStateFlow.value = PopularState.Success(dataRates.rates)
                    }
                }
            }
    }

    fun addWishlistCurrency(wishlistDao: WishlistDao) = viewModelScope.launch {
        mainRepository.addWishlistCurrency(wishlistDao).collect {
            _popularAddWishlistStateFlow.value = AddWishlistPopularState.Success
        }
    }
}