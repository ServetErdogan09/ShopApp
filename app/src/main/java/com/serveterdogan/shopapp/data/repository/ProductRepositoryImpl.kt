package com.serveterdogan.shopapp.data.repository

import com.serveterdogan.shopapp.data.mapper.toProduct
import com.serveterdogan.shopapp.data.remote.service.ProductApiService
import com.serveterdogan.shopapp.domain.model.Product
import com.serveterdogan.shopapp.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
   private val apiService: ProductApiService
) : ProductRepository {
    override suspend fun getProducts(): Result<List<Product>> {
     return try {
          val response = apiService.getProducts()
          val products = response.products.map { it.toProduct() }
         Result.success(products)
        }catch (e: Exception){
         Result.failure(e)
        }
    }

    override suspend fun getProductById(productId: Int): Result<Product> {
     return  try {
           val response = apiService.getProductById(id = productId)
           val product = response.toProduct()
         Result.success(product)
       }catch (e: Exception){
         Result.failure(e)
       }
    }

    override suspend fun searchProducts(query: String): Result<List<Product>> {
        return  try {
            val response = apiService.getSearchProduct(query = query)
            val products = response.products.map { it.toProduct() }
            Result.success(products)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}