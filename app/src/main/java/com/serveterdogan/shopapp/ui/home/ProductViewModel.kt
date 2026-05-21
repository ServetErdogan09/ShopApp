package com.serveterdogan.shopapp.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serveterdogan.shopapp.domain.model.Product
import com.serveterdogan.shopapp.domain.repository.FavoriteRepository
import com.serveterdogan.shopapp.domain.usecase.ProductUseCase
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
    private val getProductsUseCase: ProductUseCase,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private val _productState = MutableStateFlow(ProductState())
    val productState: StateFlow<ProductState> = _productState.asStateFlow()

    private var favoriteIds = emptySet<Int>()
    private var searchJob: Job? = null

    init {
        observeFavorites()
        loadProducts()
        Log.d("ProductViewModel","ProductViewmodel init")
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            favoriteRepository.getFavoriteProducts().collect { favorites ->
                favoriteIds = favorites.map { it.id }.toSet()
                // Favoriler değiştiğinde mevcut listeyi güncelle
                _productState.value = _productState.value.copy(
                    products = _productState.value.products.map { product ->
                        product.copy(isFavorite = favoriteIds.contains(product.id))

                    }

                )
            }
        }
    }

    fun loadProducts() {
        viewModelScope.launch {
            _productState.value = _productState.value.copy(isLoading = true, isError = null)
            val result = getProductsUseCase.getProducts()

            result.onSuccess { products ->
                _productState.value = _productState.value.copy(
                    products = products.map { it.copy(isFavorite = favoriteIds.contains(it.id)) },
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

    fun getSearchProduct(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            _productState.value = _productState.value.copy(isLoading = true, isError = null)
            val result = getProductsUseCase.searchProducts(query)

            result.onSuccess { products ->
                _productState.value = _productState.value.copy(
                    products = products.map { it.copy(isFavorite = favoriteIds.contains(it.id)) },
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

    fun getProductCategory(category: String) {
        if (category == "Tümü") {
            _productState.value = _productState.value.copy(selectedCategory = "Tümü")
            loadProducts()
            return
        }

        viewModelScope.launch {
            _productState.value = _productState.value.copy(
                isLoading = true,
                isError = null,
                selectedCategory = category
            )
            val result = getProductsUseCase.getProductsByCategory(category)

            result.onSuccess { products ->
                _productState.value = _productState.value.copy(
                    isLoading = false,
                    isError = null,
                    products = products.map { it.copy(isFavorite = favoriteIds.contains(it.id)) }
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

    // Favori ekleme/çıkarma işlemini Ana Sayfadan da yapabilmek için
    fun toggleFavorite(product: Product) {
        viewModelScope.launch {
            if (product.isFavorite) {
                favoriteRepository.deleteFavoriteProduct(product.id)
            } else {
                favoriteRepository.addFavoriteProduct(product)
            }
        }
    }
}