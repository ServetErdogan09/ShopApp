package com.serveterdogan.shopapp.domain.repository

import com.serveterdogan.shopapp.domain.model.Product
import kotlinx.coroutines.flow.Flow


interface FavoriteRepository {

    fun getFavoriteProducts(): Flow<List<Product>>
    suspend fun addFavoriteProduct(product: Product) : Result<Unit>
    suspend fun deleteFavoriteProduct(id: Int) : Result<Unit>

    // ürün favori mi değil mi kontrol etmek için
    fun isProductFavorite(productId: Int): Flow<Boolean>

    suspend fun refreshFavoriteProducts(): Result<Unit>


}
