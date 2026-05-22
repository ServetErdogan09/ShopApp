package com.serveterdogan.shopapp.data.repository

import com.serveterdogan.shopapp.data.local.Entity.ProductDao
import com.serveterdogan.shopapp.data.mapper.toProduct
import com.serveterdogan.shopapp.data.mapper.toProductEntity
import com.serveterdogan.shopapp.data.remote.service.ProductApiService
import com.serveterdogan.shopapp.domain.model.Product
import com.serveterdogan.shopapp.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val dao: ProductDao,
    private val apiService: ProductApiService
) : FavoriteRepository{
    override fun getFavoriteProducts(): Flow<List<Product>> {
       return dao.getFavoriteProducts().map { list->
           list.map { it.toProduct()}
       }
    }

    override suspend fun addFavoriteProduct(product: Product): Result<Unit> {
        return try {
            dao.insertProduct(product.toProductEntity())
            Result.success(Unit) // herhnagi bişi döndürmüyeceğiz Unit
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun deleteFavoriteProduct(id: Int): Result<Unit> {
        return try {
           dao.deleteProduct(id)
            Result.success(Unit)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override fun isProductFavorite(productId: Int): Flow<Boolean> {
        return dao.isFavorite(productId)
    }

    override suspend fun refreshFavoriteProducts(): Result<Unit> {
        return try {

            val currentFavorites = dao.getFavoriteProductsOnce()

            currentFavorites.forEach { entity ->
                val response = apiService.getProductById(entity.id)
                // API başarılıysa veriyi güncelle
                dao.insertProduct(response.toProduct().toProductEntity())
            }
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}
