package com.serveterdogan.shopapp.data.local.Entity

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): Flow<List<CartEntity>>

    @Query("SELECT * FROM cart_items WHERE id = :productId LIMIT 1")
    suspend fun getOneCartItem(productId: Int): CartEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartEntity: CartEntity)

    @Delete
    suspend fun deleteCartItem(cartEntity: CartEntity)

    @Query("UPDATE cart_items SET quantity = quantity - 1  WHERE id = :productId AND quantity > 1")
    suspend fun decrementIfExists(productId: Int): Int

    @Query("DELETE FROM cart_items WHERE id = :productId AND quantity = 1")
    suspend fun deleteIfSingle(productId: Int) : Int

    @Query("UPDATE cart_items SET  quantity = quantity +1 WHERE id = :productId")
    suspend fun incrementIfExists(productId: Int) : Int
    @Query("DELETE FROM cart_items WHERE id = :productId")
    suspend fun deleteCartItemById(productId: Int)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()
}
