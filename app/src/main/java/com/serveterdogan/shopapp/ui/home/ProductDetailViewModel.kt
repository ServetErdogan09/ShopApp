package com.serveterdogan.shopapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serveterdogan.shopapp.domain.repository.FavoriteRepository
import com.serveterdogan.shopapp.domain.usecase.ProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productUseCase: ProductUseCase,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private val _productState = MutableStateFlow(ProductDetailState())
    val productState: StateFlow<ProductDetailState> = _productState.asStateFlow()

    fun getProductById(productId: Int) {
        viewModelScope.launch {
            _productState.value = _productState.value.copy(isLoading = true, isError = null)
            

            val result = productUseCase.getProductById(productId)

            result.onSuccess { product ->
                _productState.value = _productState.value.copy(
                    selectedProduct = product,
                    isLoading = false,
                    isError = null
                )
                observeFavoriteStatus(productId)
            }

            result.onFailure {
                _productState.value = _productState.value.copy(
                    isLoading = false,
                    isError = it.message
                )
            }
        }
    }

    private fun observeFavoriteStatus(productId: Int) {
        viewModelScope.launch {
            favoriteRepository.isProductFavorite(productId).collect { isFavorite ->
                _productState.value = _productState.value.copy(
                    selectedProduct = _productState.value.selectedProduct?.copy(isFavorite = isFavorite)
                )
            }
        }
    }

    fun toggleFavorite() {
        val product = _productState.value.selectedProduct ?: return
        viewModelScope.launch {
            if (product.isFavorite) {
                favoriteRepository.deleteFavoriteProduct(product.id)
            } else {
                favoriteRepository.addFavoriteProduct(product)
            }
        }
    }
}