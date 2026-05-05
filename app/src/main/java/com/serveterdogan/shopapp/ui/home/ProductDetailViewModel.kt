package com.serveterdogan.shopapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serveterdogan.shopapp.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor (
    private val repository: ProductRepository
) : ViewModel() {

    private val _productState = MutableStateFlow(ProductDetailState())
    val productState : StateFlow<ProductDetailState> = _productState.asStateFlow()

    fun getProductById(productId : Int){
        viewModelScope.launch {
            _productState.value = _productState.value.copy(isLoading = true , isError = null)
            val result = repository.getProductById(productId)

            result.onSuccess { product->
                _productState.value =_productState.value.copy(
                    selectedProduct = product,
                    isLoading = false,
                    isError = null

                )
            }

            result.onFailure {
                _productState.value =_productState.value.copy(
                    isLoading = false,
                    isError = it.message

                )
            }
        }
    }

}