package com.serveterdogan.shopapp.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serveterdogan.shopapp.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

      private val _productState = MutableStateFlow(ProductState())
        val  productState : StateFlow<ProductState> = _productState.asStateFlow()


    private var searchJob : Job? = null

    init {
        loadProducts()
        Log.d("ProductViewModel", "ProductViewModel init")
    }


    fun loadProducts(){
        viewModelScope.launch {
            _productState.value = _productState.value.copy(isLoading = true , isError = null)
            val result = productRepository.getProducts()

            result.onSuccess { products->
                _productState.value = _productState.value.copy(
                    products = products,
                    isLoading = false,
                    isError = null
                )
            }

            result.onFailure {
                Log.d("ProductViewModel", "ProductViewModel onFailure : ${it.message}")

                _productState.value = _productState.value.copy(
                    isLoading = false,
                    isError = it.message
                )
            }
        }
    }



    fun getProductById(productId : Int){
        viewModelScope.launch {
            _productState.value = _productState.value.copy(isLoading = true , isError = null)
            val result = productRepository.getProductById(productId)

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


    fun getSearchProduct(query : String) {
        Log.d("ProductViewModel", "ProductViewModel getSearchProduct : $query")
        searchJob?.cancel() // önceki aramayı iptal et

      searchJob =   viewModelScope.launch {

            delay(500L) // kullanıcı yazmasını 500 ms bekleyelim
           _productState.value = _productState.value.copy(isLoading = true, isError = null)
            val result = productRepository.searchProducts(query)


            result.onSuccess { products->
                _productState.value = _productState.value.copy(
                    products = products,
                    isLoading = false,
                    isError = null
                )
            }

            result.onFailure {
                _productState.value = _productState.value.copy(
                    isLoading = false,
                    isError = it.message
                )
            }
        }
    }
}