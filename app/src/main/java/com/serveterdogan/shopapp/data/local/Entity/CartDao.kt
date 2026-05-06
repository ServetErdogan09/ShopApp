package com.serveterdogan.shopapp.data.local.Entity

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): Flow<List<CartEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartEntity: CartEntity)

    @Delete
    suspend fun deleteCartItem(cartEntity: CartEntity)

    @Query("DELETE FROM cart_items WHERE id = :productId")
    suspend fun deleteCartItemById(productId: Int)

    @Query("UPDATE cart_items SET quantity = :newQuantity WHERE id = :productId")
    suspend fun updateQuantity(productId: Int, newQuantity: Int)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()
}
