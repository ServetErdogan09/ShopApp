package com.serveterdogan.shopapp.ui.cart

import com.serveterdogan.shopapp.data.local.Entity.CartEntity

data class CartState(
    val cartItems: List<CartEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
) {
    val totalPrice: Double
        get() = cartItems.sumOf { it.price * it.quantity }
    
    val totalItems: Int
        get() = cartItems.sumOf { it.quantity }
}
