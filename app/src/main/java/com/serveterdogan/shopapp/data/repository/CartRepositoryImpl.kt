package com.serveterdogan.shopapp.data.repository

import android.util.Log
import com.serveterdogan.shopapp.data.local.Entity.CartDao
import com.serveterdogan.shopapp.data.local.Entity.CartEntity
import com.serveterdogan.shopapp.domain.model.Product
import com.serveterdogan.shopapp.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao
) : CartRepository {

    override fun getCartItems(): Flow<List<CartEntity>> = cartDao.getAllCartItems()

    override suspend fun addToCart(product: Product) {

        //val existingItem = cartDao.getOneCartItem(product.id)
        val update = cartDao.incrementIfExists(productId = product.id)

        Log.d("CartRepositoryImpl", "addToCart called with productId: ${product.id}")
        if (update == 0) {
            cartDao.insertCartItem(
                CartEntity(
                    id = product.id,
                    title = product.title,
                    price = product.price,
                    thumbnail = product.thumbnail,
                    quantity = 1,
                    category = product.category ?: "",
                    brand = product.brand
                )
            )
        }
    }

    override suspend fun removeFromCart(productId: Int) {
        Log.d("CartRepositoryImpl", "removeFromCart called with productId: $productId")
        cartDao.deleteCartItemById(productId)
    }

    override suspend fun incrementQuantity(productId: Int) {
        Log.d("CartRepositoryImpl", "incrementQuantity called with productId: $productId")
       cartDao.incrementIfExists(productId = productId)
    }

    override suspend fun decrementQuantity(productId: Int) {
        Log.d("CartRepositoryImpl", "decrementQuantity called with productId: $productId")
       val update = cartDao.decrementIfExists(productId = productId)

        if(update == 0){
            cartDao.deleteIfSingle(productId = productId)
        }
    }

    override suspend fun clearCart() {
        cartDao.clearCart()
    }
}
