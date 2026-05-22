package com.serveterdogan.shopapp.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serveterdogan.shopapp.domain.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CartState())
    val state = _state.asStateFlow()

    private val _uiEvent = kotlinx.coroutines.flow.MutableSharedFlow<String>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        getCartItems()
    }

    private fun getCartItems() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            cartRepository.getCartItems().collect { items ->
                _state.value = _state.value.copy(
                    cartItems = items,
                    isLoading = false
                )
            }
        }
    }

    fun incrementQuantity(productId: Int) {
        viewModelScope.launch {
            cartRepository.incrementQuantity(productId)
        }
    }

    fun decrementQuantity(productId: Int) {
        viewModelScope.launch {
            cartRepository.decrementQuantity(productId)
        }
    }

    fun addToCart(product: com.serveterdogan.shopapp.domain.model.Product) {
        viewModelScope.launch {
            cartRepository.addToCart(product)
            _uiEvent.emit("${product.title} sepete eklendi")
        }
    }

    fun removeFromCart(productId: Int) {
        viewModelScope.launch {
            cartRepository.removeFromCart(productId)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            cartRepository.clearCart()
        }
    }
}
