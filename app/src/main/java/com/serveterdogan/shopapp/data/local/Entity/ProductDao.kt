package com.serveterdogan.shopapp.data.local.Entity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM products")
    fun getFavoriteProducts() : Flow<List<ProductEntity>> // Favoriler değiştiği zaman bu listeyi günceller anlık olarak bize veriyor

    @Query("SELECT * FROM products")
    suspend fun getFavoriteProductsOnce(): List<ProductEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE) // eğer aynı id ye sahip bir ürün varsa onu güncele
    suspend fun insertProduct(productEntity: ProductEntity)


    @Query("DELETE FROM products WHERE id = :id")
    suspend fun deleteProduct(id: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM products WHERE id = :id)")
    fun isFavorite(id: Int): Flow<Boolean>
}

