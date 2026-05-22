package com.serveterdogan.shopapp.domain.repository

import com.serveterdogan.shopapp.data.local.Entity.CartEntity
import com.serveterdogan.shopapp.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCartItems(): Flow<List<CartEntity>>
    suspend fun addToCart(product: Product)
    suspend fun removeFromCart(productId: Int)
    suspend fun incrementQuantity(productId: Int)
    suspend fun decrementQuantity(productId: Int)
    suspend fun clearCart()
}
